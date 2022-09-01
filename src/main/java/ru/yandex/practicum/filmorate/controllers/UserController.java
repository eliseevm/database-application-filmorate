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
import java.util.Set;

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

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@RequestParam String id, @RequestParam String friendId) {
        return userService.addFriend(id, friendId);
    }

    @GetMapping()
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}/friends")
    public Set<Long> getListFriends(@RequestParam String id) {
       return userService.getFriends().get(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<Long>getListCommonFriends(@RequestParam String id, @RequestParam String otherId) {
       return  userService.getListCommonFriends(id, otherId);
    }
    @DeleteMapping("/{id}/friends/{friendId}")
    public User delFromFriend(@RequestParam String id, @RequestParam String friendId) {
       return userService.delFromFriend(id, friendId);
    }
}
