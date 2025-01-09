package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {

    Optional<Genre> getGenres();

    Optional<Genre> getGenreById(Integer genreId);

    List<Genre> getGenresByFilmId(Integer filmId);


}
