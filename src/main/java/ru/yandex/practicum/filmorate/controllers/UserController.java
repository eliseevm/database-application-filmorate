package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@Getter
@RequestMapping("/users")
public class UserController {
   private int id = 1;

    private final HashMap<Integer, User> users = new HashMap<>();


    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        validate(user);
        return users.get(id-1);
    }

    public void validate (User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@") || user.getLogin().contains(" ")
                || user.getLogin().isEmpty() || user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Пользователь указал свои параметры с недостатками!");
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
        }
    }
    @PutMapping()
    public User putUsers (@Valid @RequestBody User user) {
        if ((users.get(user.getId()).equals(null))) {
            users.put(id, user);
            id++;
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
    @GetMapping()
    public List<User> getAll () {
        List<User> userss = new ArrayList<>(users.values());
        System.out.println("55555  " + userss.size());
        return userss;
    }
}
