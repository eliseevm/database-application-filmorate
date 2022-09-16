package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    // Внедряем зависимость inMemoryUserStorage через конструктор.
    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    // Проверяем параметры пользователя и вносим в список фильмов.
    public User validate(User user) {
        if (inMemoryUserStorage.getUsers().containsKey(user.getId())) {
            log.error("Пользователь с таким номером не найден ");
            throw new NotFoundException("Пользователь с таким номером не найден ");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@") || user.getLogin().contains(" ")
                || user.getLogin().isEmpty() || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Пользователь указал свои параметры с недостатками! ");
            throw new InvalidUserException("Ошибочное заполнение полей пользователя! ");
        }
        if (user.getName() == null || user.getName().equals(" ") || user.getName().equals("")) {
            user.setName(user.getLogin());
            user.setId(inMemoryUserStorage.getId());
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            inMemoryUserStorage.setId(inMemoryUserStorage.getId() + 1);
            log.info("Пользователь с '{}' успешно добавлен ", user.getId());
        } else {
            user.setId(inMemoryUserStorage.getId());
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            inMemoryUserStorage.setId(inMemoryUserStorage.getId() + 1);
            log.info("В список пользователей добавлен новый пользователь  с id = '{}' ! ", user.getId());
        }
        return inMemoryUserStorage.getUsers().get(user.getId());
    }

    // Обновление параметров пользователя.
    public User upDateUsers(User user) {
        if (!inMemoryUserStorage.getUsers().containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с таким номером id = " + user.getId() + "не найден ");
        }
        if ((!inMemoryUserStorage.getUsers().containsKey(user.getId()))) {
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            log.info("В список пользователей добавлен пользоваьель с id '{}' ", user.getId());
            return inMemoryUserStorage.getUsers().get(user.getId());
        } else {
            User oldUser = inMemoryUserStorage.getUsers().get(user.getId());
            oldUser.setId(user.getId());
            oldUser.setName(user.getName());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setEmail(user.getEmail());
            oldUser.setLogin(user.getLogin());
            log.info("В списке пользователей изменены данные пользователя с id '{}' ", user.getId());
            return oldUser;
        }
    }

    // Получение списка всех пользователей.
    public List<User> getAll() {
        log.info("Количество пользователей приложения составляет '{}' человек ",
                inMemoryUserStorage.getUsersList().size());
        return inMemoryUserStorage.getUsersList();
    }

    // Получение списка друзей, пользователя с id.
    public List<User> getFriends(Long id) {
        List<User> users = new ArrayList<>();
        Set<Long> v = new HashSet<>();
        v = inMemoryUserStorage.getUsers().get(id).getFriends();
        for (Long user : v) {
            User us = inMemoryUserStorage.getUsers().get(user);
            users.add(us);
        }
        return users;
    }

    // Получение списка общех друзей двух пользователей.
    public Set<User> getListCommonFriends(Long id, Long otherId) {
        if (!inMemoryUserStorage.getUsers().containsKey(id) || !inMemoryUserStorage.getUsers().containsKey(otherId)) {
            log.error("Пользователей с id = '{}' и  otherId = '{}' не найдено ", id, otherId);
            throw new NotFoundException("Пользователей с такими id не найдено ");
        } else {
            List<Long> firstList = new ArrayList<>(inMemoryUserStorage.getUsers().get(id).getFriends());
            List<Long> secondList = new ArrayList<>(inMemoryUserStorage.getUsers().get(otherId).getFriends());
            Set<User> finalSortList = new HashSet<>();
            if (firstList.size() > secondList.size()) {
                firstList.retainAll(secondList);
                for (Long key : firstList) {
                    finalSortList.add(inMemoryUserStorage.getUsers().get(key));
                }
                log.info("Получаем список общихдрузей для пользователей '{}' и '{}' ", id, otherId);
                return finalSortList;
            } else {
                secondList.retainAll(firstList);
                for (Long idi : secondList) {
                    finalSortList.add(inMemoryUserStorage.getUsers().get(idi));
                }
                log.info("Получаем список общихдрузей для пользователей '{}' и '{}' ", id, otherId);
                return finalSortList;
            }
        }
    }

    // Добавляем друга в список друзей пользователя.
    public void addFriend(Long id, Long friendId) {
        if (friendId < 0 || !inMemoryUserStorage.getUsers().containsKey(id)
                || !inMemoryUserStorage.getUsers().containsKey(friendId)) {
            throw new NotFoundException("Пользователей с такими id не найдено ");
        } else {
            User user1 = inMemoryUserStorage.getUsers().get(id);
            User user2 = inMemoryUserStorage.getUsers().get(friendId);
            user1.addFriends(friendId);
            log.info("Пользователь с id '{}' добавил в друзья пользователя '{}' ",
                    id, friendId);
            user2.addFriends(id);
            log.info("Пользователь с id '{}' добавил в друзья пользователя '{}' ", friendId, id);
        }
    }

    // Удаленик пользователя из списка друзей.
    public void delFromFriend(Long id, Long friendId) {
        if (friendId < 0 || !inMemoryUserStorage.getUsers().containsKey(id)
                || !inMemoryUserStorage.getUsers().containsKey(friendId)) {
            throw new NotFoundException("Пользователей с такими id не найдено ");
        } else {
            Set<Long> friends = inMemoryUserStorage.getUsers().get(id).getFriends();
            friends.remove(friendId);
            log.info("Пользователь с id '{}' удалил из друзей пользователя с id '{}' ", friendId, id);
            Set<Long> friends1 = inMemoryUserStorage.getUsers().get(friendId).getFriends();
            friends1.remove(id);
            log.info("Пользователь с id '{}' удалил из друзей пользователя с id '{}' ",
                    id, friendId);
        }
    }

    // Получаем пользователя по номеру.
    public User getUserById(Long id) {
        if (!inMemoryUserStorage.getUsers().containsKey(id)) {
            throw new NotFoundException("Пользоватеоя с таким id не найдено");
        }
        return inMemoryUserStorage.getUsers().get(id);
    }
}
