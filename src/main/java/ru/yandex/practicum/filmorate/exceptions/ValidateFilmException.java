package ru.yandex.practicum.filmorate.exceptions;

public class ValidateFilmException extends RuntimeException {
    public ValidateFilmException(final String massage) {
        super(massage);
    }
}
