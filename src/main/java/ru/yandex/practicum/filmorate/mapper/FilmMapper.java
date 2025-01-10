package ru.yandex.practicum.filmorate.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class FilmMapper {

    public static FilmDto mapToFilmDto(Film film) {
        if (film == null) {
            return null;
        }

        Set<Genre> uniqueGenres = new HashSet<>();
        if (film.getGenres() != null) {
            uniqueGenres = new HashSet<>(film.getGenres());
        }

        List<Genre> genresList = uniqueGenres.stream().collect(Collectors.toList());

        return FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .genres(genresList)
                .mpa(film.getMpa())
                .build();
    }
}

