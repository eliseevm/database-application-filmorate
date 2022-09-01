package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private final HashMap<Integer, Set<Long>> friends = new HashMap<>();
    List<User> usersList = new ArrayList<>(users.values());

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public List<User>getUsersList() {
        return usersList;
    }

    @Override
    public HashMap<Integer, Set<Long>> getFriends() {
        return friends;
    }

    @Override
    public User setFriend(String id, String friendId) {
       Set<Long> userFriends = new HashSet<>();
       userFriends.add(Long.parseLong(friendId));
       friends.put(Integer.parseInt(id), userFriends);
       return users.get(Integer.parseInt(friendId));
    }
}
