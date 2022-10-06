package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

public interface FilmStorage {
    LocalDate getCheckData();

    Long getId();

    void setId(Long id);

    Map<Long, Film> getFilms();
}
