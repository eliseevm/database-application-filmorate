package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

@Component
public class InMemoryUserStorage implements UserStorage {
}
