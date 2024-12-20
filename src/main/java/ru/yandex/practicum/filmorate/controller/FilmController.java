package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;
import ru.yandex.practicum.filmorate.service.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@Slf4j
@Validated
@RequestMapping("/films")
public class FilmController {

    private final FilmServiceImpl filmServiceImpl;
    private final UserServiceImpl userServiceImpl;

    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmController(FilmServiceImpl filmServiceImpl, UserServiceImpl userServiceImpl,
                          InMemoryFilmStorage filmStorage) {
        this.filmServiceImpl = filmServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmStorage.getFilms();
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmStorage.createFilm(film);
        if (createdFilm == null) {
            log.error("Фильм не был создан");
            throw new ValidationException("Не удалось создать фильм");
        }

        return ResponseEntity.ok().body(createdFilm);
    }


    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        Film filmUpdated = filmStorage.updateFilm(film);
        if (filmUpdated != null) {
            return ResponseEntity.ok().body(filmUpdated);
        } else {
            log.error("Фильм с ID: {} не найден или не удалось обновить", film.getId());
            throw new NotFoundException("Фильм: " + film.getId() + " не найден");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        Film film = filmStorage.getFilmById(id);
        if (film != null) {
            return ResponseEntity.ok().body(film);
        } else {
            log.error("Фильм с ID: {} не найден", id);
            throw new NotFoundException("Фильм с ID: " + id + " не найден");
        }
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> likeFilm(@PathVariable int id, @PathVariable int userId) {
        filmServiceImpl.addLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        filmServiceImpl.removeLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<Collection<Film>> getMostPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        if (count <= 0) {
            throw new ValidationException("Значение size должно быть больше нуля");
        }
        return ResponseEntity.ok().body(filmServiceImpl.getMostPopularFilms(count));
    }

}

