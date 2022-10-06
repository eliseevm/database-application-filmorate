package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    Map<Long, User> getUsers();

    List<User> getUsersList();

    Long getId();

    void setId(Long id);
}
