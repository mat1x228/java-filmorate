package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.interfaces.FilmServiceImpl;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@Slf4j
@Validated
@RequestMapping("/films")
public class FilmController {

    private final FilmServiceImpl filmServiceImpl;

    @Autowired
    public FilmController(FilmServiceImpl filmServiceImpl) {
        this.filmServiceImpl = filmServiceImpl;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("Получение всех фильмов");
        return filmServiceImpl.getFilms();
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmServiceImpl.createFilm(film);
        log.info("Создание фильма");
        if (createdFilm == null) {
            log.error("Фильм не был создан");
            throw new ValidationException("Не удалось создать фильм");
        }

        return ResponseEntity.ok().body(createdFilm);
    }


    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма с ID: {}", film.getId());
        Film filmUpdated = filmServiceImpl.updateFilm(film);
        if (filmUpdated != null) {
            return ResponseEntity.ok().body(filmUpdated);
        } else {
            log.error("Фильм с ID: {} не найден или не удалось обновить", film.getId());
            throw new NotFoundException("Фильм: " + film.getId() + " не найден");
        }
    }

}
