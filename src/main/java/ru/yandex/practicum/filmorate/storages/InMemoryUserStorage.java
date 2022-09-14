package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import java.util.*;

@Component
public class  InMemoryUserStorage implements UserStorage {
    private long id = 1;
    private final Map<Long, User> users = new HashMap<>();

    List<User> usersList;

    @Override
    public Map<Long, User> getUsers() {
        return users;
    }

    @Override
    public List<User>getUsersList() {
        usersList = new ArrayList<>(users.values());
        return usersList;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
