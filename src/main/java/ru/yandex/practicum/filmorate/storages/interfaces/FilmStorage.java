package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

public interface FilmStorage {
    public LocalDate getCHECK_DATA();
    public Long getId();
    public void setId(Long id);
    public Map<Long, Film> getFilms();
}
