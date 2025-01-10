package ru.yandex.practicum.filmorate.dto;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FilmDto {
    Integer id;
    String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
    List<Genre> genres;
    Mpa mpa;
}
