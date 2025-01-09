package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmDbStorage;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    @Autowired
    private final FilmDbStorage filmStorage;
    @Autowired
    private final UserStorage userStorage;


    public void addLike(int filmId, int userId) {
//        Optional<Film> film = filmStorage.getFilmById(filmId);
//        User user = userStorage.getUserById(userId);
//
//        log.info("Пользователь {} поставил лайк фильму {}", userId, filmId);
//        if (user != null) {
//            film.addLike(user.getId());
//        } else {
//            log.warn("Пользователь не авторизован");
//            throw new NotFoundException("Юзер с ID: " + userId + " не найден");
//        }
    }

    public void removeLike(int filmId, int userId) {
//        Film film = filmStorage.getFilmById(filmId);
//        User user = userStorage.getUserById(userId);
//
//        log.info("Пользователь {} удалил лайк фильму {}", userId, filmId);
//        if (user != null) {
//            film.deleteLike(user.getId());
//
//        } else {
//            log.warn("Пользователь не авторизован");
//            throw new NotFoundException("Юзер с ID: " + userId + " не найден");
//        }
    }

    public List<FilmDto> getMostPopularFilms(int count) {
        log.info("Получение {} самых популярных фильмов", count);
        return filmStorage.getFilms().stream()
                .filter(f -> f.getLikes().size() > 0)
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto createFilm(Film film) {
        Film createdFilm = filmStorage.createFilm(film);
        if (createdFilm == null) {
            log.error("Фильм не был создан");
            throw new ValidationException("Не удалось создать фильм");
        }

        return FilmMapper.mapToFilmDto(createdFilm);
    }

    public List<FilmDto> getFilms() {
        return filmStorage.getFilms().stream()
                .map(FilmMapper::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto updateFilm(Film film) {
        if (film == null || film.getId() == null) {
            log.error("Пустое тело запроса или отсутствует ID фильма");
            throw new NotFoundException("Фильм не найден: тело запроса пустое или ID отсутствует");
        }

        Film filmUpdated = filmStorage.updateFilm(film);
        if (filmUpdated != null) {
            return FilmMapper.mapToFilmDto(filmUpdated);
        } else {
            log.error("Фильм с ID: {} не найден или не удалось обновить", film.getId());
            throw new NotFoundException("Фильм: " + film.getId() + " не найден");
        }
    }


    public FilmDto getFilmById(int id) {
        return filmStorage.getFilmById(id)
                .map(FilmMapper::mapToFilmDto)
                .orElseThrow(() -> new NotFoundException("Фильм с ID: " + id + " не найден"));
    }

}
