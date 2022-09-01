package ru.yandex.practicum.filmorate.storages;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    List<User> usersList = new ArrayList<>(users.values());

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public List<User>getUsersList() {
        return usersList;
    }
}
