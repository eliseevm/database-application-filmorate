package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final LocalDate CHECK_DATA = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();
    private Long id = 0L;
@Override
    public LocalDate getCHECK_DATA() {
        return CHECK_DATA;
    }
@Override
    public Long getId() {
        return ++id;
    }
@Override
    public void setId(Long id) {
        this.id = id;
    }
@Override
    public Map<Long, Film> getFilms() {
    return films;
}
}
