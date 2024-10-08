package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.interfaces.FilmServiceImpl;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


@RestController
@Slf4j
@Validated
@RequestMapping("/films")
public class FilmController {

    private FilmServiceImpl filmServiceImpl = new FilmServiceImpl();

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("Получение всех фильмов: " + filmServiceImpl.getFilms().size());
        List<Film> films = filmServiceImpl.getFilms();
        return films;
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmServiceImpl.createFilm(film);

        if (createdFilm == null) {
            log.error("Фильм не был создан");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createdFilm);
        }

        log.info("Фильм создан: {}", film.getName());
        log.trace("Название фильма: {}, Описание фильма: {}, Дата выхода фильма: {}, Продолжительность фильма: {}",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());

        return ResponseEntity.ok().body(createdFilm);
    }


    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма с ID: {}", film.getId());
        Film filmUpdated = filmServiceImpl.updateFilm(film);
        if (filmUpdated != null) {
            log.trace("Название фильма: {}, Описание фильма: {}, Дата выхода фильма: {}, Продолжительность фильма: {}",
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
            return ResponseEntity.ok().body(filmUpdated);
        } else {
            log.error("Фильм с ID: {} не найден или не удалось обновить", film.getId());
            Film notFoundFilm = new Film();
            notFoundFilm.setName("Фильм не существует");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundFilm);
        }
    }

}
