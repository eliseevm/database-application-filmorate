package ru.yandex.practicum.filmorate.storages.interfaces;


import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface UserStorage {
    HashMap<Integer, User> getUsers();
    List<User> getUsersList();
    HashMap<Integer, Set<Long>> getFriends();
    User setFriend(String id, String friendId);
}
