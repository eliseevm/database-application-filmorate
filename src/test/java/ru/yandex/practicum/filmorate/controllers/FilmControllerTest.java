/*package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;
    @Autowired
    public FilmControllerTest (FilmController filmController) {
        this.filmController = filmController;
    }

    @Test
    void validate() {
        Set<Long> likes = new HashSet<>();
        likes.add(1L);
        likes.add(2L);
        likes.add(3L);
        likes.add(0L);
       // final FilmController filmController = new FilmController();
        Film film = new Film(0, "", "Фантастика о снах"
                , LocalDate.of(2018, 12, 15), 120, likes);
        Film film1 = new Film(1, "Начало", "Фантастика о снах вапа ывапввп вапыва вапывап " +
                "ывапывапбва вавап,ва ываыва,ыва ываыывапыввап" +
                ". Пываыв ывапы dsgfsdfg sdfgsdfg sdfg sdfg sdfgdfssdefgs sdfg " +
                " sdfgsdfg ssdfg вап ывывап ывапывап вап ывапывап ыва"
                , LocalDate.of(2018, 12, 15), 120, likes);
        Film film2 = new Film(0, "Начало", "Фантастика о снах"
                , LocalDate.of(1895, 12, 15), 120, likes);
        Film film3 = new Film(0, "Начало", "Фантастика о снах"
                , LocalDate.of(2018, 12, 15), -5, likes);
        assertThrows(RuntimeException.class, () -> filmController.create(film));
        assertThrows(RuntimeException.class, () -> filmController.create(film1));
        assertThrows(RuntimeException.class, () -> filmController.create(film2));
        assertThrows(RuntimeException.class, () -> filmController.create(film3));
    }
}*/