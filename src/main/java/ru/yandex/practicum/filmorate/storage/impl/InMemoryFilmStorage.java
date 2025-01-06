package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
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
    public Film getFilmById(int id) {
        log.info("Получение фильма с ID: {}", id);
        if (!filmStorage.containsKey(id)) {
            log.error("Фильм с ID: {} не найден", id);
            throw new NotFoundException("Фильм не найден");
        }
        return filmStorage.get(id);
    }


}
