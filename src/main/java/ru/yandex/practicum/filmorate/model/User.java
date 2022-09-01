package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private InMemoryUserStorage inMemoryUserStorage;
    private Set<Long> frinds;
    private Integer id;
    @NotBlank
    private String login;
    private String name;
    @Email
    private String email;
    private LocalDate birthday;
    @Autowired
    public User (InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.frinds = inMemoryUserStorage.getFriends().get(id);
    }
}
