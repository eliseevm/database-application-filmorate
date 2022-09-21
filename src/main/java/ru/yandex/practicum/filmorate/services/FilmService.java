package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidateFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;

import java.util.*;

@Service
@Slf4j
public class FilmService {
    private FilmStorage inMemoryFilmStorage;

    /* Внедряем зависимость inMemoryFilmStorage через конструктор, прм этом FilmService зависит от интерфейса
     FilmStorage, а не от его реализации.*/
    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    // Проверяем и добавляем фильм в список фильмов.
    public Film validate(Film film) {
        if (film.getName() == null || ("").equals(film.getName()) || film.getDescription().length() > 200
                || film.getReleaseDate().isBefore(inMemoryFilmStorage.getCHECK_DATA()) || film.getDuration() <= 0) {
            log.error("При добавлении фильма, ведены не правильные параметры фильма, фильм не добавлен. ");
            throw new ValidateFilmException("Не верные параметры фильма, фильм не добавлен. ");
        } else {
            film.setId(inMemoryFilmStorage.getId());
            inMemoryFilmStorage.getFilms().put(film.getId(), film);
            log.info("В список фильмов добавлен новый фильм с id '{}'. ", film.getId());
        }
        return inMemoryFilmStorage.getFilms().get(film.getId());
    }

    // Обновляем параметры фильма.
    public Film update(Film film) {
        if (film.getId() < 0 || !inMemoryFilmStorage.getFilms().containsKey(film.getId())) {
            log.error("ID '{}' фильма неправильное или не существует. ", film.getId());
            throw new NotFoundException("Фильм с id = " + film.getId() + " не найден. ");
        } else if (film == null) {
            throw new RuntimeException("Ошибка пользователя, не введен фильм");
        } else if (inMemoryFilmStorage.getFilms().containsKey(film.getId())) {
            inMemoryFilmStorage.getFilms().put(film.getId(), film);
            log.info("Фильм с id = " + film.getId() + " успешно обновлен. ");
        }
        return inMemoryFilmStorage.getFilms().get(film.getId());
    }

    // Возвращаем фильм по id.
    public Film getFilmById(Long id) {
        if (id < 0 || !inMemoryFilmStorage.getFilms().containsKey(id)) {
            log.error("ID '{}' фильма неправильное или не существует. ", id);
            throw new NotFoundException("Фильма с таким id " + id + " не существует");
        } else {
            log.info("Фильм с id '{}' успешно возвращен ", id);
            return inMemoryFilmStorage.getFilms().get(id);
        }
    }

    // Возвращаем копию списока фильмов.
    public List<Film> getFilmList() {
        List<Film> filmList = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        log.info("Список фильмов возвращен, количество фильмов в списке составляет '{}' фильмов. ", filmList.size());
        return filmList;
    }

    // Возвращаем список популярных фильмов с заданным количеством count.
    public List<Film> getPopularFilm(Integer count) {
        if (count <= 0) {
            log.error("Ввдено не правильное количество фильмов в списке. ");
            throw new NotFoundException(" Количество фильмов в списке не может быть равно или меньше нуля. ");
        }
        FilmComparator filmComparator = new FilmComparator();
        List<Film> listFilm = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        listFilm.sort(filmComparator);
        if (count > listFilm.size()) {
            count = listFilm.size();
        }
        listFilm = listFilm.subList(0, count);
        log.info("Получен размер списка популярных фильмов с количеством '{}'фильмов. ", listFilm.size());
        return listFilm;
    }

    // Добавляем лайк от пользователя с userId, в список лайков для фильма с id.
    public void addLikeToFilm(Long id, Long userId) {
        if (!inMemoryFilmStorage.getFilms().containsKey(id)) {
            log.error("Фильма с таким id = '{}' нет в списке", id);
            throw new NotFoundException("Фильма с таким id = " + id + " нет в списке");
        } else {
            Film film = inMemoryFilmStorage.getFilms().get(id);
            film.addLikes(userId);
            film.setRate();
            log.info("Лайк от пользователя с userId = '{}', для фильма с id = '{}' успешно добавлен", userId, id);
        }
    }

    // Удаляем лайк от пользователя с userId, из списка лайков для фильма с id.
    public Long delLikeToFilm(Long id, Long userId) {
        if (userId < 0 || !inMemoryFilmStorage.getFilms().get(id).getLikes().contains(userId)) {
            log.error("Фильма с указанным id = '{}' нет в списке", id);
            throw new NotFoundException("Такого фильма нет");
        } else {
            log.info("Лайк от пользователя с userId = '{}' оставленный для фильма с id = '{}', успешно удален", userId, id);
            inMemoryFilmStorage.getFilms().get(id).getLikes().remove(userId);
            return userId;
        }
    }

    // Класс сортировщик фильмов в зависимости от рейтинга
    static class FilmComparator implements Comparator<Film> {
        @Override
        public int compare(Film film1, Film film2) {
            return film2.getRate() - film1.getRate();
        }
    }
}

