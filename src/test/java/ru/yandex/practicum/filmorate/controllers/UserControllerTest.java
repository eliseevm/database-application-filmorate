package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void validateTest() throws InvalidUserException {
        final UserController userController = new UserController();
        final User user = new User(null, "Muha", "Max", "eliseev@bk.ru", LocalDate.of(1997, 10, 25));
        userController.create(user);
        assertEquals("Max", userController.getUsers().get(1).getName());
        assertEquals("eliseev@bk.ru", userController.getUsers().get(1).getEmail());
        assertEquals(1, userController.getUsers().get(1).getId());
        assertEquals("Muha", userController.getUsers().get(1).getLogin());
        assertEquals(LocalDate.of(1997, 10, 25), userController.getUsers().get(1).getBirthday());
    }

    @Test
    void validateEmailExceptionTest() throws InvalidUserException {
        final UserController userController = new UserController();
        final User user = new User(null, "Muha", "Max", "eliseevbk.ru", LocalDate.of(1997, 10, 25));
        final User user1 = new User(null, "Muha", "Max", "", LocalDate.of(1997, 10, 25));
        assertThrows(RuntimeException.class, () -> userController.validate(user));
        assertThrows(RuntimeException.class, () -> userController.validate(user1));
    }

    @Test
    void validateLoginExceptionTest() throws InvalidUserException {
        final UserController userController = new UserController();
        final User user = new User(null, "Muha", "Max", "eliseev@bk.ru",  LocalDate.of(1997, 10, 25));
        final User user1 = new User(null, "eliseev@bk.ru", "  ", "Max", LocalDate.of(1997, 10, 25));
        final User user2 = new User(null, "eliseev@bk.ru", "Mu ha", "Max", LocalDate.of(1997, 10, 25));
        assertThrows(RuntimeException.class, () -> userController.validate(user));
        assertThrows(RuntimeException.class, () -> userController.validate(user1));
        assertThrows(RuntimeException.class, () -> userController.validate(user2));
    }

    @Test
    void validateNameExceptionTest() throws InvalidUserException {
        final UserController userController = new UserController();
        final User user = new User(null , "Muha", "",  "eliseev@bk.ru", LocalDate.of(1997, 10, 25));
        final User user1 = new User(null, "Muha", " ", "eliseev@bk.ru", LocalDate.of(1997, 10, 25));
        userController.create(user);
        userController.create(user1);
        assertEquals("Muha", userController.getUsers().get(1).getName());
        assertEquals("Muha", userController.getUsers().get(2).getName());
    }

    @Test
    void validateBirthdayExceptionTest() {
        final UserController userController = new UserController();
        final User user = new User(1, "eliseev@bk.ru", "Muha", "Max", LocalDate.of(2025, 10, 25));
        assertThrows(RuntimeException.class, () -> userController.validate(user));
    }
}