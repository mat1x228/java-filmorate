package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@Slf4j
@Validated
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    @Autowired
    private final FilmService filmService;


    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getFilms();
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok().body(filmService.createFilm(film));
    }


    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok().body(filmService.updateFilm(film));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        return ResponseEntity.ok().body(filmService.getFilmById(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> likeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.removeLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<Collection<Film>> getMostPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        if (count <= 0) {
            throw new ValidationException("Значение size должно быть больше нуля");
        }
        return ResponseEntity.ok().body(filmService.getMostPopularFilms(count));
    }

}

