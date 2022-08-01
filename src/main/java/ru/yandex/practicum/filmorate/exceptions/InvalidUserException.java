package ru.yandex.practicum.filmorate.exceptions;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException (final String massage) {
        super(massage);
    }
}
