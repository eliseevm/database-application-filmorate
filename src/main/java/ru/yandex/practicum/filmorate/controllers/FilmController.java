package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;
import javax.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidateFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    FilmService filmService;

    // Внедрение зависимости filmService через конструктор
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Добавляем фильм
    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        return filmService.validate(film);
    }

    // Обновление сведений о фильме
    @PutMapping()
    public Film update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable String id, @PathVariable String userId) {
        filmService.addLikeToFilm(Long.parseLong(id), Long.parseLong(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Long delLikeToFilm(@PathVariable String id, @PathVariable String userId) {
        return filmService.delLikeToFilm(Long.parseLong(id), Long.parseLong(userId));
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable String id) {
        return filmService.getFilmById(Long.parseLong(id));
    }

    @GetMapping()
    public List<Film> getAll() {
        return filmService.getFilmList();
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(required = false, defaultValue = "1") String count) {
        return filmService.getPopularFilm(Integer.parseInt(count));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(final ValidationException ex) {
        return Map.of("Произошло исключение", "Пользователь с таким id не найден");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handle1(final NotFoundException ex) {
        return Map.of("Произошло исключение", "Пользователь с таким id не найден");
    }

}
