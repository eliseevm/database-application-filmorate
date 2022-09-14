package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private InMemoryUserStorage inMemoryUserStorage;
    private Set<Long> friends = new HashSet<>();
    private Long id;
    @NotBlank
    private String login;
    private String name;
    @Email
    private String email;
    private LocalDate birthday;
    @Autowired
    public User (InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    public void addFriends (Long id) {
        this.friends.add(id);
    }
    public Set<Long> getFriends() {
        Set<Long> f = new HashSet<>(friends);
        return f;
    }
}
