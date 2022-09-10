package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidateFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    public Long addLikeToFilm(@PathVariable String id, @PathVariable String userId) {
        return filmService.addLikeToFilm(Long.parseLong(id), Long.parseLong(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Long delLikeToFilm(@PathVariable String id, @PathVariable String userId) {
        return filmService.delLikeToFilm(Long.parseLong(id), Long.parseLong(userId));
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable String id) {
      return filmService.getFilmById(Long.parseLong(id));
    }

    @GetMapping()
    public List<Film> getAll() {
        return filmService.getFilmList();
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(required = false, defaultValue = "10") String count) {
        return  filmService.getPopularFilm(Integer.parseInt(count));
    }

}
