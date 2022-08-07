package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final static LocalDate CHECK_DATA = LocalDate.of(1895, 12, 28);
    private final HashMap<Integer, Film> films = new HashMap<Integer, Film>();
    private Integer id = 1;

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        validate(film);
        return films.get(id - 1);
    }

    private void validate(Film film) {
        if (film.getName().isEmpty() || film.getDescription().length() > 200
                || film.getReleaseDate().isBefore(CHECK_DATA) || film.getDuration() <= 0) {
            log.error("Введены неправильные параметры фильма, фильм не сохранен в списке");
            throw new ValidateFilmException("Не верные параметры фильма");
        } else {
            film.setId(id);
            films.put(id, film);
            id++;
            log.info("В список фильмов добавлен новый фильм с id '{}'", film.getId());
        }
    }

    @PutMapping()
    public Film update(@RequestBody Film film) {
        if (film.getId() <= 0) {
            log.error("ID '{}' фильма неправильное", film.getId());
            throw new ValidateFilmException("Не верные параметры фильма");
        }
        Film oldFilm = films.get(film.getId());
        if (!films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            oldFilm.setDuration(film.getDuration());
            oldFilm.setDescription(film.getDescription());
            oldFilm.setName(film.getName());
            oldFilm.setReleaseDate(film.getReleaseDate());
            log.info("Изменён фильм с ID '{}' в списке фильмов.", film.getId());
        }
        return oldFilm;
    }

    @GetMapping()
    public List<Film> getAll() {
        List<Film> filmsList = new ArrayList<>(films.values());
        log.info("Количество фильмов в списке составляет '{}' фильмов.", filmsList);
        return filmsList;
    }
}
