package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

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
        if (user.getEmail() == null || !user.getEmail().contains("@") || user.getLogin().contains(" ")
                || user.getLogin().isEmpty() || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Пользователь указал свои параметры с недостатками!");
            throw new InvalidUserException("Ошибочное заполнение полей пользователя!");
        } if (user.getName() == null || user.getName().equals(" ") || user.getName().equals("")) {
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
        if ((!inMemoryUserStorage.getUsers().containsKey(user.getId()))) {
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            return inMemoryUserStorage.getUsers().get(user.getId());
        } else {
            User oldUser = inMemoryUserStorage.getUsers().get(user.getId());
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

    public Set<Long> getFriends(Long id) {
        return inMemoryUserStorage.getFriends().get(id);
    }

    public List<Long> getListCommonFriends(Long id, Long otherId) {
        List<Long> firstList = new ArrayList<>(inMemoryUserStorage.getFriends().get(id));
        List<Long> secondList = new ArrayList<>(inMemoryUserStorage.getFriends().get(otherId));
        if (firstList.size() > secondList.size()) {
            firstList.retainAll(secondList);
            return firstList;
        } else {
            secondList.retainAll(firstList);
            return secondList;
        }
    }

    public User addFriend(Long id, Long friendId) {
        log.info("Пользователь с id '{}' добавил в друзья пользователя '{}'",
                id, friendId);
        Set<Long> userFriends = new HashSet<>();
        userFriends.add(friendId);
        inMemoryUserStorage.setFriends(id, userFriends);
        return inMemoryUserStorage.getUsers().get(friendId);
    }

    public User delFromFriend(Long id, Long friendId) {
        log.info("Пользователь с id '{}' удалил из друзей пользователя '{}'",
                id, friendId);
        return inMemoryUserStorage.getUsers().get(friendId);
    }
    public User getUserById(Long id) {
        return inMemoryUserStorage.getUsers().get(id);
    }
}
