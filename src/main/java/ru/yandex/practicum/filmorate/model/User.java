package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private InMemoryUserStorage inMemoryUserStorage;
    private Set<Long> friends = new HashSet<>();
    private Long id;
    private boolean friendStatus;
    @NotBlank
    private String login;
    private String name;
    @Email
    private String email;
    private LocalDate birthday;

    // Внедряем зависимость через конструктор.
    @Autowired
    public User(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    // Получаем друзей для этого пользователя.
    public void addFriends(Long id) {
        this.friends.add(id);
    }

    // Отдаем копию списка друзей этого пользователя.
    public Set<Long> getFriends() {
        Set<Long> copyList = new HashSet<>(friends);
        return copyList;
    }
}
