package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
@Qualifier("InMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private static final HashMap<Integer, Film> filmStorage = new HashMap<>();
    private static final AtomicInteger FILM_ID_HOLDER = new AtomicInteger();

    @Override
    public Film createFilm(Film film) {
        log.info("Создание фильма");
        final int filmId = FILM_ID_HOLDER.incrementAndGet();
        film.setId(filmId);
        filmStorage.put(filmId, film);

        log.info("Фильм создан: {}", film.getName());
        log.trace("Название фильма: {}, Описание фильма: {}, Дата выхода фильма: {}, Продолжительность фильма: {}",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());

        return filmStorage.get(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Обновление фильма с ID: {}", film.getId());
        if (filmStorage.containsKey(film.getId())) {
            filmStorage.put(film.getId(), film);
            log.trace("Название фильма: {}, Описание фильма: {}, Дата выхода фильма: {}, Продолжительность фильма: {}",
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
            return filmStorage.get(film.getId());
        } else {
            return null;
        }
    }

    @Override
    public List<Film> getFilms() {
        Collection<Film> values = filmStorage.values();
        log.info("Получение всех фильмов: " + values.size());
        return new ArrayList<>(values);
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        log.info("Получение фильма с ID: {}", id);
        Film film = filmStorage.get(id);
        if (film == null) {
            log.warn("Фильм с ID: {} не найден", id);
            throw new NotFoundException("Фильм с ID: " + id + " не найден");
        }
        return Optional.of(film);
    }


    @Override
    public void addLike(int filmId, int userId) {

    }

    @Override
    public void deleteLike(int filmId, int userId) {

    }


}
