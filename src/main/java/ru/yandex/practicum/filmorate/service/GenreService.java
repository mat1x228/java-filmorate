package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();

    GenreDto getGenreById(Integer genreId);
}
