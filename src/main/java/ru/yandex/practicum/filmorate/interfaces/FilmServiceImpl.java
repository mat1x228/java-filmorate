package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FilmServiceImpl implements FilmService {

    private static HashMap<Integer, Film> filmStorage = new HashMap<Integer, Film>();

    private static final AtomicInteger FILM_ID_HOLDER = new AtomicInteger();


    public  Film createFilm(Film film) {
        final int filmId = FILM_ID_HOLDER.incrementAndGet();
        film.setId(filmId);
        filmStorage.put(filmId, film);
        return filmStorage.get(filmId);
    }

    @Override
    public List<Film> getFilms() {
        Collection<Film> values = filmStorage.values();
        return new ArrayList<>(values);
    }

    @Override
    public Film updateFilm(Film film) {
        if(filmStorage.containsKey(film.getId())) {
            Integer filmId = film.getId();
            filmStorage.remove(film.getId());
            filmStorage.put(filmId, film);
            return filmStorage.get(filmId);
        }
        return null;
    }

}