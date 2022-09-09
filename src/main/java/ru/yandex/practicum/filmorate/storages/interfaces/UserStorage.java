package ru.yandex.practicum.filmorate.storages.interfaces;


import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserStorage {
    Map<Long, User> getUsers();
    List<User> getUsersList();
    Map<Long, Set<Long>> getFriends();
    void setFriends(Long id, Set<Long> userFriends);
    public Long getId();
    public void setId(Long id);
}
