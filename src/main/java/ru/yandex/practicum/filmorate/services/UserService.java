package ru.yandex.practicum.filmorate.services;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class UserService {

    private UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User validate(User user) {
        if (inMemoryUserStorage.getUsers().containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с таким номером не найден");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@") || user.getLogin().contains(" ")
                || user.getLogin().isEmpty() || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Пользователь указал свои параметры с недостатками!");
            throw new ValidationException("Ошибочное заполнение полей пользователя!");
        }
        if (user.getName() == null || user.getName().equals(" ") || user.getName().equals("")) {
            user.setName(user.getLogin());
            user.setId(inMemoryUserStorage.getId());
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            inMemoryUserStorage.setId(inMemoryUserStorage.getId() + 1);
        } else {
            user.setId(inMemoryUserStorage.getId());
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            inMemoryUserStorage.setId(inMemoryUserStorage.getId() + 1);
            log.info("В список пользователей добавлен новый пользователь  с id = '{}' !", user.getId());
        }
        return inMemoryUserStorage.getUsers().get(user.getId());
    }

    public User upDateUsers(User user) {
        if (!inMemoryUserStorage.getUsers().containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с таким номером не найден");
        }
        if ((!inMemoryUserStorage.getUsers().containsKey(user.getId()))) {
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            return inMemoryUserStorage.getUsers().get(user.getId());
        } else {
            User oldUser = inMemoryUserStorage.getUsers().get(user.getId());
            oldUser.setId(user.getId());
            oldUser.setName(user.getName());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setEmail(user.getEmail());
            oldUser.setLogin(user.getLogin());
            log.info("В списке пользователей изменены данные пользователя с id '{}'", user.getId());
            return oldUser;
        }
    }

    public List<User> getAll() {
        log.info("Количество пользователей приложения составляет '{}' человек.",
                inMemoryUserStorage.getUsersList().size());
        return inMemoryUserStorage.getUsersList();
    }

    public List<User> getFriends(Long id) {
        List<User> users = new ArrayList<>();
        Set<Long> v = inMemoryUserStorage.getUsers().get(id).getFriends();
        for(Long user : v) {
            User us = inMemoryUserStorage.getUsers().get(user);
            users.add(us);
        } return users;
    }

    public Set<Long> getListCommonFriends(Long id, Long otherId) {
        if (!inMemoryUserStorage.getUsers().containsKey(id) || !inMemoryUserStorage.getUsers().containsKey(otherId)) {
            throw new NotFoundException("Пользователей с такими id не найдено");
        } else {
            Set<Long> firstList = inMemoryUserStorage.getUsers().get(id).getFriends();
            Set<Long> secondList = inMemoryUserStorage.getUsers().get(otherId).getFriends();
            if (firstList.size() > secondList.size()) {
                firstList.retainAll(secondList);
                return firstList;
            } else {
                secondList.retainAll(firstList);
                System.out.println("PRINT ddd" + secondList);
                return secondList;
            }
        }
    }

    public User addFriend(Long id, Long friendId) {
        if (!inMemoryUserStorage.getUsers().containsKey(id) || !inMemoryUserStorage.getUsers().containsKey(friendId)) {
            throw new NotFoundException("Пользователей с такими id не найдено");
        }
        log.info("Пользователь с id '{}' добавил в друзья пользователя '{}'",
                id, friendId);
        User user1 = inMemoryUserStorage.getUsers().get(id);
        user1.addFriends(friendId);
        User user2 = inMemoryUserStorage.getUsers().get(friendId);
        user2.addFriends(id);
        return inMemoryUserStorage.getUsers().get(id);
    }

    public void delFromFriend(Long id, Long friendId) {
        Set<Long>friends = inMemoryUserStorage.getUsers().get(id).getFriends();
        friends.remove(friends);
        Set<Long>friends1 = inMemoryUserStorage.getUsers().get(friendId).getFriends();
        friends1.remove(id);
        log.info("Пользователь с id '{}' удалил из друзей пользователя '{}'",
                id, friendId);
    }

    public User getUserById(Long id) {
        if (!inMemoryUserStorage.getUsers().containsKey(id)) {
            throw new NotFoundException("Пользоватеоя с таким id не найдено");
        }
        return inMemoryUserStorage.getUsers().get(id);
    }
}
