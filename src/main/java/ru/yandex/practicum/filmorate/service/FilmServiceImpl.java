package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {

    InMemoryFilmStorage filmStorage;
    InMemoryUserStorage userStorage;

    @Autowired
    public FilmServiceImpl(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);

        log.info("Пользователь {} поставил лайк фильму {}", userId, filmId);
        if (user != null) {
            film.addLike(user.getId());
        } else {
            log.warn("Пользователь не авторизован");
            throw new NotFoundException("Юзер с ID: " + userId + " не найден");
        }
    }

    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);

        log.info("Пользователь {} удалил лайк фильму {}", userId, filmId);
        if (user != null) {
            film.deleteLike(user.getId());

        } else {
            log.warn("Пользователь не авторизован");
            throw new NotFoundException("Юзер с ID: " + userId + " не найден");
        }
    }

    public List<Film> getMostPopularFilms(int count) {
        log.info("Получение {} самых популярных фильмов", count);
        return filmStorage.getFilms().stream()
                .filter(f -> f.getLikes().size() > 0)
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
