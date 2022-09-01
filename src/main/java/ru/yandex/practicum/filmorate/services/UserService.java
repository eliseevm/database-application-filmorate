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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private UserStorage inMemoryUserStorage;
    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
       this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User validate(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@") || user.getLogin().contains(" ")
                || user.getLogin().isEmpty() || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Пользователь указал свои параметры с недостатками!");
            throw new InvalidUserException("Недостатки при заполнении полей пользователя!");
        } else if (user.getName().isEmpty() || user.getName().equals(" ")) {
            user.setName(user.getLogin());
            inMemoryUserStorage.getUsers().put(user.getId(), user);
        } else {
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            log.info("В список пользователей добавлен новый пользователь!");
        } return inMemoryUserStorage.getUsers().get(user.getId());
    }

    public User upDateUsers(User user) {
        if ((!inMemoryUserStorage.getUsers().containsKey(user.getId()))) {
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            //id++;
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
        log.info( "Количество пользователей приложения составляет '{}' человек.",
                inMemoryUserStorage.getUsersList().size());
        return inMemoryUserStorage.getUsersList();
    }
}
