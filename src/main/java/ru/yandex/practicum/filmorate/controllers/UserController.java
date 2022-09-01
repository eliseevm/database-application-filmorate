package ru.yandex.practicum.filmorate.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@Getter
@RequestMapping("/users")
public class UserController {
   UserService userService;
   @Autowired
   public UserController(UserService userService) {
       this.userService = userService;
   }


    @PostMapping()
    public User create(@Valid @RequestBody User user) {
     return userService.validate(user);
    }

    @PutMapping()
    public User putUsers(@RequestBody User user) {
       return userService.upDateUsers(user);
    }

    @GetMapping()
    public List<User> getAll() {
        return userService.getAll();
    }
}
