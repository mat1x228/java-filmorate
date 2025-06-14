package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
// import ru.yandex.practicum.filmorate.model.User; // User is not used in this test class after removing setUp
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet; // Required for initializing film likes
// import java.util.Set; // Set is not directly used, HashSet is sufficient for Film constructor

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException; // Added for new tests
import static org.mockito.ArgumentMatchers.anyInt; // Added for never() with anyInt()

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {


    @Mock
    private FilmServiceImpl filmService;

    @InjectMocks
    private FilmController filmController;

    // private Film film1; // These are initialized in setUp, which is removed.
    // private Film film2; // Re-initialize in tests or with @BeforeEach if needed for multiple tests.
    // private User user1; // Not used

    // @BeforeEach // setUp method is removed
    // public void setUp() {
        // MockitoAnnotations.openMocks(this); // This is replaced by @ExtendWith(MockitoExtension.class)
        // Set<Integer> firstLikes = new HashSet<>();
        // Set<Integer> secondLikes = new HashSet<>();
        // film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, firstLikes);
        // film2 = new Film(2, "Film 2", "Description 2", LocalDate.of(2022, 2, 1), 130, secondLikes);
        // user1 = new User(1, "John Doe", "john.doe@example.com", "johndoe", LocalDate.of(1990, 1, 1), null);
    // }

    @Test
    void testGetAllFilms() {
        Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, null); // Initialized here
        Film film2 = new Film(2, "Film 2", "Description 2", LocalDate.of(2022, 2, 1), 130, null); // Initialized here
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
        // film1 needs to be initialized for this test
        Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, new HashSet<>());
        // User is not directly used by filmController.likeFilm, but filmService.addLike might need a user.
        // However, filmService is mocked, so we only need to ensure getFilmById is properly mocked.
        when(filmService.getFilmById(1)).thenReturn(film1);
        // filmService.addLike is a void method, no need to mock its return if not checked.
        // If it throws an exception, the test would fail as expected.

        ResponseEntity<Void> response = filmController.likeFilm(1, 1); // Assuming filmId=1, userId=1

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(filmService, times(1)).addLike(1, 1); // Verify addLike was called
    }

    @Test
    void testRemoveLikeFromFilm() {
        // film1 needs to be initialized for this test
        Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.of(2022, 1, 1), 120, new HashSet<>());
        when(filmService.getFilmById(1)).thenReturn(film1);
        // filmService.removeLike is a void method.

        // filmController.likeFilm(1, 1); // This call in a removeLike test might be a copy-paste error.
        // We are testing removeLike, so we should call unlikeFilm.
        // If the intention was to ensure a like exists before removing, the state should be set up in the mock or film object.
        // For this test, we assume the like exists or the removeLike method handles it.

        ResponseEntity<Void> response = filmController.unlikeFilm(1, 1); // Assuming filmId=1, userId=1

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(filmService, times(1)).removeLike(1, 1); // Verify removeLike was called
    }

    @Test
    void testGetMostPopularFilms() {
        // film1 and film2 are not directly used here unless filmService.getMostPopularFilms uses them.
        // The mock for getMostPopularFilms should return a list of films.
        Film popularFilm = new Film(1, "Popular Film", "Desc", LocalDate.now(), 100, new HashSet<>());
        when(filmService.getMostPopularFilms(1)).thenReturn(Arrays.asList(popularFilm));


        ResponseEntity<Collection<Film>> response = filmController.getMostPopularFilms(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().contains(popularFilm));
        verify(filmService, times(1)).getMostPopularFilms(1);
    }

    @Test
    void testUpdateFilm_Success() {
        Film filmToUpdate = new Film(1, "Updated Film", "Updated Desc", LocalDate.of(2000, 1, 1), 100, null);
        when(filmService.updateFilm(any(Film.class))).thenReturn(filmToUpdate);

        ResponseEntity<Film> response = filmController.updateFilm(filmToUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filmToUpdate, response.getBody());
        verify(filmService, times(1)).updateFilm(filmToUpdate);
    }

    @Test
    void testUpdateFilm_NotFound() {
        Film filmToUpdate = new Film(99, "Unknown Film", "Unknown Desc", LocalDate.of(2000, 1, 1), 100, null);
        when(filmService.updateFilm(any(Film.class))).thenThrow(new NotFoundException("Film not found"));

        assertThrows(NotFoundException.class, () -> {
            filmController.updateFilm(filmToUpdate);
        });
        verify(filmService, times(1)).updateFilm(filmToUpdate);
    }

    @Test
    void testGetFilmById_Success() {
        Film film = new Film(1, "Test Film", "Test Desc", LocalDate.of(2001, 1, 1), 110, null);
        when(filmService.getFilmById(1)).thenReturn(film);

        ResponseEntity<Film> response = filmController.getFilmById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(film, response.getBody());
        verify(filmService, times(1)).getFilmById(1);
    }

    @Test
    void testGetFilmById_NotFound() {
        when(filmService.getFilmById(99)).thenThrow(new NotFoundException("Film not found"));

        assertThrows(NotFoundException.class, () -> {
            filmController.getFilmById(99);
        });
        verify(filmService, times(1)).getFilmById(99);
    }

    @Test
    void testGetMostPopularFilms_InvalidCount() {
        // The controller itself throws ValidationException for count <= 0
        assertThrows(ValidationException.class, () -> {
            filmController.getMostPopularFilms(0);
        });
        // Verify filmService.getMostPopularFilms is not called
        verify(filmService, never()).getMostPopularFilms(anyInt());
    }
}
