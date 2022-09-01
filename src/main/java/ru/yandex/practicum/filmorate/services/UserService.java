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
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;
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
            user.setId(id);
            users.put(id, user);
            id++;
        } else {
            user.setId(id);
            users.put(id, user);
            id++;
            log.info("В список пользователей добавлен новый пользователь!");
        } return users.get(id - 1);
    }

    public User upDateUsers(User user) {
        if ((!users.containsKey(user.getId()))) {
            users.put(user.getId(), user);
            //id++;
            return users.get(user.getId());
        } else {
            User oldUser = users.get(user.getId());
            oldUser.setName(user.getName());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setEmail(user.getEmail());
            oldUser.setLogin(user.getLogin());
            log.info("В списке пользователей изменены данные пользователя с id '{}'", user.getId());
            return oldUser;
        }
    }

    public List<User> getAll() {
        List<User> usersList = new ArrayList<>(users.values());
        log.info( "Количество пользователей приложения составляет '{}' человек.", usersList.size());
        return usersList;
    }
}
