package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    List<FilmDto> getMostPopularFilms(int limit);

    FilmDto createFilm(Film film);

    List<FilmDto> getFilms();

    FilmDto updateFilm(Film film);

    FilmDto getFilmById(int id);

}