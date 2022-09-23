package ru.yandex.practicum.filmorate.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@Getter
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    // Внедряем зависимость userService через конструктор.
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Добавляем пользователя.
    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    // Обновляем данные пользователя.
    @PutMapping()
    public User putUsers(@RequestBody User user) {
        return userService.upDateUsers(user);
    }

    // Добавляем друга с friendId в список друзей пользователя с id.
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.addFriend(Long.parseLong(id), Long.parseLong(friendId));
    }

    // Получаем пользователя по id.
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    // Получаем список всех пользователей.
    @GetMapping()
    public List<User> getAll() {
        return userService.getAll();
    }

    // Получаем список друзей пользователя по id пользователя.
    @GetMapping("/{id}/friends")
    public List<User> getListFriends(@PathVariable String id) {
        return userService.getFriends(Long.parseLong(id));
    }

    // Получаем список общих друзей двух пользователей.
    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getListCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getListCommonFriends(Long.parseLong(id), Long.parseLong(otherId));
    }

    // Удоляем пользователя с friendId из списка друзей пользователя с id.
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.delFromFriend(Long.parseLong(id), Long.parseLong(friendId));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(final InvalidUserException ex) {
        return Map.of("Произошло исключение", "Не правильные параметры запроса.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handle1(final NotFoundException ex) {
        return Map.of("Произошло исключение", "Искомый объект не найден");
    }
}
