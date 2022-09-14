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


    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film validate(Film film) {
        if (film.getName() == null || film.getName().equals("") || film.getDescription().length() > 200
                || film.getReleaseDate().isBefore(inMemoryFilmStorage.getCHECK_DATA()) || film.getDuration() <= 0) {
            throw new ValidateFilmException("Не верные параметры фильма");
        } else {
            film.setId(inMemoryFilmStorage.getId());
            inMemoryFilmStorage.getFilms().put(film.getId(), film);
            log.info("В список фильмов добавлен новый фильм с id '{}'", film.getId());
        }
        System.out.println("PRINT FILM2" + film);
        return inMemoryFilmStorage.getFilms().get(film.getId());
    }

    public Film update(Film film) {
        if (film.getId() < 0) {
            log.error("ID '{}' фильма неправильное", film.getId());
            throw new NotFoundException("Фильм с id = '{}' не найден");
        } else if (inMemoryFilmStorage.getFilms().containsKey(film.getId())) {
            Map<Long, Film> temp = inMemoryFilmStorage.getFilms();
            temp.put(film.getId(), film);
        }
        return inMemoryFilmStorage.getFilms().get(film.getId());
    }

    public Film getFilmById(Long id) {
        if (id < 0) {
            throw new NotFoundException("Фильма с таким id " + id + " не существует");
        } else {
            return inMemoryFilmStorage.getFilms().get(id);
        }
    }

    public List<Film> getFilmList() {
        List<Film> filmList = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        log.info("Количество фильмов в списке составляет '{}' фильмов.", filmList);
        return filmList;
    }

    public List<Film> getPopularFilm(Integer count) {
        FilmComparator filmComparator = new FilmComparator();
        List<Film> listFilm = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        System.out.println("Печатаю размер списка популярных фильмов до сортировки " + listFilm);
        listFilm.sort(filmComparator);
        if(count > listFilm.size()) {
            count = listFilm.size();
        }
        listFilm = listFilm.subList(0, count);
        System.out.println("Печатаю размер списка популярных фильмов" + listFilm);
        return listFilm;
    }

    public void addLikeToFilm(Long id, Long userId) {
       /* if (!inMemoryFilmStorage.getFilms().containsKey(id) || userService.getUserById(userId).equals(null)) {
            throw new NotFoundException("Внутреняя ошибка сервера");
        } else {*/
        System.out.println("Печатаем список фильмов" + inMemoryFilmStorage.getFilms());
        Film film = inMemoryFilmStorage.getFilms().get(id);
        //Set<Long> t = film.getLikes();
      //  t.add(userId);
        film.addLikes(userId);
        film.setRate(inMemoryFilmStorage.getFilms().get(id).getLikes().size());
        System.out.println("Печатаю размер списка" + inMemoryFilmStorage.getFilms().get(id).getLikes().size());
    }

    public Long delLikeToFilm(Long id, Long userId) {
        if(userId < 0 || !inMemoryFilmStorage.getFilms().get(id).getLikes().contains(userId)) {
            throw new NotFoundException("Лайка от пользователя с этим номером нет");
        } else {
            inMemoryFilmStorage.getFilms().get(id).getLikes().remove(userId);
            return userId;
        }
    }

    static class FilmComparator implements Comparator<Film> {
        @Override
        public int compare(Film film1, Film film2) {
            return film2.getRate() - film1.getRate();
        }
    }
}

