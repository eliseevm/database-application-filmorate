package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@ToString
@Setter
@Getter
@AllArgsConstructor
public class Film {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long>likes = new HashSet<>();
    private Integer rate;
    InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public Film(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
    public void addLikes(Long id) {
        this.likes.add(id);
    }
    public void setRate(Integer rate) {
        this.rate = this.rate + rate;
    }
}
