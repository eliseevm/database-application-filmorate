package ru.yandex.practicum.filmorate.storages.interfaces;


import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserStorage {
    HashMap<Integer, User> getUsers();
    List<User> getUsersList();
}
