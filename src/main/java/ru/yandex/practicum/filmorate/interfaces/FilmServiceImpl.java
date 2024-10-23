package ru.yandex.practicum.filmorate.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {

    private static final HashMap<Integer, Film> filmStorage = new HashMap<>();
    private static final AtomicInteger FILM_ID_HOLDER = new AtomicInteger();

    @Override
    public Film createFilm(Film film) {
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

}