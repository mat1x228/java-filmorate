package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.interfaces.FilmServiceImpl;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class FilmorateApplicationTests {

	@Mock
	private FilmServiceImpl filmServiceImpl;

	@InjectMocks
	private FilmController filmController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	void testGetAllFilms_whenEmptyList_returnsEmptyList() {
		when(filmServiceImpl.getFilms()).thenReturn(Collections.emptyList());

		List<Film> films = (List<Film>) filmController.getAllFilms();

		assertEquals(Collections.emptyList(), films);
	}

	@Test
	void testGetAllFilms_whenListWithFilms_returnsFilmsList() {
		Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);
		Film film2 = new Film(2, "Film 2", "Description 2", LocalDate.of(2022, 2, 1), 130);
		List<Film> films = List.of(film1, film2);

		when(filmServiceImpl.getFilms()).thenReturn(films);

		List<Film> resultFilms = (List<Film>) filmController.getAllFilms();

		assertEquals(films, resultFilms);
	}

	@Test
	void testCreateFilm_whenFilmIsValid_returnsCreatedFilm() {
		Film film = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);
		Film createdFilm = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);

		when(filmServiceImpl.createFilm(film)).thenReturn(createdFilm);

		ResponseEntity<Film> responseEntity = filmController.createFilm(film);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(createdFilm, responseEntity.getBody());
	}

	@Test
	void testCreateFilm_whenFilmIsInvalid_throwsResponseStatusException() {
		Film film = new Film(1, null, "Description 1", LocalDate.of(2022, 1, 1), 120);

		when(filmServiceImpl.createFilm(film)).thenReturn(null);

		assertThrows(ResponseStatusException.class, () -> filmController.createFilm(film));
	}

	@Test
	void testUpdateFilm_whenFilmIsValid_returnsOkStatus() {
		Film film = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);

		when(filmServiceImpl.updateFilm(film)).thenReturn(film);

		ResponseEntity<Film> responseEntity = filmController.updateFilm(film);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testUpdateFilm_whenFilmIsInvalid_throwsResponseStatusException() {
		Film film = new Film(1, null, "Description 1", LocalDate.of(2022, 1, 1), 120);

		when(filmServiceImpl.updateFilm(film)).thenReturn(null);

		assertThrows(ResponseStatusException.class, () -> filmController.updateFilm(film));
	}

	@Test
	void testUpdateFilm_whenFilmNotFound_throwsResponseStatusException() {
		Film film = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120);

		when(filmServiceImpl.updateFilm(film)).thenReturn(null);

		assertThrows(ResponseStatusException.class, () -> filmController.updateFilm(film));
	}


}
