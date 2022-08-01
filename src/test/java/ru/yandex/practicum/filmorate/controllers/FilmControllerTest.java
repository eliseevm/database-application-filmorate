package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    void validate() {
        final FilmController filmController = new FilmController();
        Film film = new Film(0, "", "Фантастика о снах", LocalDate.of(2018, 12, 15), 120);
        Film film1= new Film(1, "Начало", "Фантастика о снахвапа ывапввп вапыва вапывап " +
           "ывапывапбва вавап,ва ываыва,ыва ываыывапыввап" +
           ". Пываыв ывапы dsgfsdfg sdfgsdfg sdfg sdfg sdfgdfssdefgs sdfg  sdfgsdfg ssdfg вап ывывап ывапывап вап ывапывап ыва", LocalDate.of(2018, 12, 15), 120);
        Film film2 = new Film(0, "Начало", "Фантастика о снах", LocalDate.of(1895, 12, 15), 120);
        Film film3 = new Film(0, "Начало", "Фантастика о снах", LocalDate.of(2018, 12, 15), -5);
        assertThrows(RuntimeException.class, () -> filmController.validate(film));
        assertThrows(RuntimeException.class, () -> filmController.validate(film1));
        assertThrows(RuntimeException.class, () -> filmController.validate(film2));
        assertThrows(RuntimeException.class, () -> filmController.validate(film3));
    }
}