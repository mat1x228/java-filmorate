package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    List<Film> getMostPopularFilms(int limit);

    Film createFilm(Film film);

    List<Film> getFilms();

    Film updateFilm(Film film);

    Film getFilmById(int id);

}