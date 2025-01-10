package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepo;
import ru.yandex.practicum.filmorate.dal.UserRepo;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    @Autowired
    private final FilmRepo filmStorage;
    @Autowired
    private final UserRepo userStorage;


    public void addLike(int filmId, int userId) {
        Optional<Film> filmOptional = filmStorage.getFilmById(filmId);
        Optional<User> userOptional = userStorage.getUserById(userId);

        log.info("Пользователь {} поставил лайк фильму {}", userId, filmId);

        if (filmOptional.isPresent() && userOptional.isPresent()) {
            filmStorage.addLike(filmId, userId);
            log.info("Лайк добавлен пользователем {} к фильму {}", userId, filmId);
        } else {
            if (!filmOptional.isPresent()) {
                throw new NotFoundException("Фильм с ID: " + filmId + " не найден");
            }
            if (!userOptional.isPresent()) {
                throw new NotFoundException("Юзер с ID: " + userId + " не найден");
            }
        }
    }


    public void removeLike(int filmId, int userId) {
        Optional<Film> filmOptional = filmStorage.getFilmById(filmId);
        Optional<User> userOptional = userStorage.getUserById(userId);

        log.info("Пользователь {} пытается убрать лайк к фильму {}", userId, filmId);

        if (filmOptional.isPresent() && userOptional.isPresent()) {
            filmStorage.deleteLike(filmId, userId);
            log.info("Лайк пользователя {} убран от фильма {}", userId, filmId);
        } else {
            if (!filmOptional.isPresent()) {
                throw new NotFoundException("Фильм с ID: " + filmId + " не найден");
            }
            if (!userOptional.isPresent()) {
                throw new NotFoundException("Юзер с ID: " + userId + " не найден");
            }
        }
    }


    public List<FilmDto> getMostPopularFilms(int count) {
        log.info("Получение {} самых популярных фильмов", count);
        return filmStorage.getMostPopularFilms(count).stream()
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
