package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Set<Long> frinds;
    private Integer id;
    @NotBlank
    private String login;
    private String name;
    @Email
    private String email;
    private LocalDate birthday;
}
