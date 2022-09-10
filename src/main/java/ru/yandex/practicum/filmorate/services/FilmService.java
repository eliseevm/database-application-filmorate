package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidateFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;

import java.util.*;

@Service
@Slf4j
public class FilmService {
    FilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film validate(Film film) {
        if (film.getName() == null || film.getDescription().length() > 200
                || film.getReleaseDate().isBefore(inMemoryFilmStorage.getCHECK_DATA()) || film.getDuration() <= 0) {
            log.error("Введены неправильные параметры фильма, фильм не сохранен в списке");
            throw new ValidateFilmException("Не верные параметры фильма");
        } else {
            film.setId(inMemoryFilmStorage.getId());
            inMemoryFilmStorage.getFilms().put(film.getId(), film);
            inMemoryFilmStorage.setId(inMemoryFilmStorage.getId() + 1);
            log.info("В список фильмов добавлен новый фильм с id '{}'", film.getId());
        }
        return inMemoryFilmStorage.getFilms().get(film.getId());
    }

    public Film update(Film film) {
        if (film.getId() < 0) {
            log.error("ID '{}' фильма неправильное", film.getId());
            throw new ValidateFilmException("Не верные параметры фильма");
        } else if (!inMemoryFilmStorage.getFilms().containsKey(film.getId())) {
                inMemoryFilmStorage.getFilms().put(film.getId(), film);
            }
        return inMemoryFilmStorage.getFilms().get(film.getId());
    }

    public Film getFilmById(Long id) {
        return inMemoryFilmStorage.getFilms().get(id);
    }

    public List<Film> getFilmList() {
        List<Film> filmList = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        log.info("Количество фильмов в списке составляет '{}' фильмов.", filmList);
        return filmList;
    }

    public List<Film> getPopularFilm(Integer count) {
        FilmComparator filmComparator = new FilmComparator();
        List<Film> listFilm = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        listFilm.sort(filmComparator);
        return listFilm.subList(0, count);
    }

    public Long addLikeToFilm(Long id, Long userId) {
        inMemoryFilmStorage.getFilms().get(id).getLikes().add(userId);
        inMemoryFilmStorage.getFilms().get(id).setRate(inMemoryFilmStorage.getFilms().get(id).getLikes().size());
        return userId;
    }

    public Long delLikeToFilm(Long id, Long userId) {
        inMemoryFilmStorage.getFilms().get(id).getLikes().remove(userId);
        return userId;
    }
    static   class FilmComparator implements Comparator <Film> {
        @Override
        public int compare (Film film1, Film film2) {
            return film2.getRate() - film1.getRate();
        }
    }
}

