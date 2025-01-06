package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmorateApplicationTests {


    @Mock
    private FilmServiceImpl filmService;

    @InjectMocks
    private FilmController filmController;

    private Film film1;
    private Film film2;

    private User user1;

    @BeforeEach
    @SuppressWarnings("resource")
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Set<Integer> firstLikes = new HashSet<>();
        Set<Integer> secondLikes = new HashSet<>();
        film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, firstLikes);
        film2 = new Film(2, "Film 2", "Description 2", LocalDate.of(2022, 2, 1), 130, secondLikes);
        user1 = new User(1, "John Doe", "john.doe@example.com", "johndoe", LocalDate.of(1990, 1, 1), null);

    }

    @Test
    void testGetAllFilms() {
        Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, null);
        Film film2 = new Film(2, "Film 2", "Description 2", LocalDate.of(2022, 2, 1), 130, null);
        when(filmService.getFilms()).thenReturn(Arrays.asList(film1, film2));

        Collection<Film> result = filmController.getAllFilms();

        assertEquals(2, result.size());
        assertTrue(result.contains(film1));
        assertTrue(result.contains(film2));
        verify(filmService, times(1)).getFilms();
    }

    @Test
    void testCreateFilm() {
        Film film = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, null);
        when(filmService.createFilm(film)).thenReturn(film);

        ResponseEntity<Film> response = filmController.createFilm(film);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(film, response.getBody());
        verify(filmService, times(1)).createFilm(film);
    }

    @Test
    void testCreateFilmFailure() {
        Film film = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, null);
        when(filmService.createFilm(film)).thenThrow(new ValidationException("Validation failed"));

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    void testAddLikeToFilm() {
        when(filmService.getFilmById(film1.getId())).thenReturn(film1);

        ResponseEntity<Void> response = filmController.likeFilm(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveLikeFromFilm() {
        when(filmService.getFilmById(film1.getId())).thenReturn(film1);

        filmController.likeFilm(1, 1);

        ResponseEntity<Void> response = filmController.unlikeFilm(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetMostPopularFilms() {
        when(filmService.getFilmById(film1.getId())).thenReturn(film1);
        when(filmService.getFilmById(film2.getId())).thenReturn(film2);

        ResponseEntity<Collection<Film>> response = filmController.getMostPopularFilms(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
