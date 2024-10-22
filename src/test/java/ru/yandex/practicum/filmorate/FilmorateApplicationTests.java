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
import ru.yandex.practicum.filmorate.interfaces.FilmServiceImpl;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmorateApplicationTests {

    @Mock
    private FilmServiceImpl filmService;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFilms() {
        Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);
        Film film2 = new Film(2, "Film 2", "Description 2", LocalDate.of(2022, 2, 1), 130);
        when(filmService.getFilms()).thenReturn(Arrays.asList(film1, film2));

        Collection<Film> result = filmController.getAllFilms();

        assertEquals(2, result.size());
        assertTrue(result.contains(film1));
        assertTrue(result.contains(film2));
        verify(filmService, times(1)).getFilms();
    }

    @Test
    void testCreateFilm() {
        Film film = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);
        when(filmService.createFilm(film)).thenReturn(film);

        ResponseEntity<Film> response = filmController.createFilm(film);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(film, response.getBody());
        verify(filmService, times(1)).createFilm(film);
    }

    @Test
    void testCreateFilmFailure() {

        Film film = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);
        when(filmService.createFilm(film)).thenReturn(null);

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        verify(filmService, times(1)).createFilm(film);
    }

}
