package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FilmControllerTest {

    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    @Captor
    private ArgumentCaptor<Film> filmCaptor;

    private Film validFilm;
    private FilmDto expectedFilmDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validFilm = Film.builder()
                .id(1)
                .name("Valid Film")
                .description("Description of the film")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(10)
                .genres(null)
                .mpa(null)
                .build();

        expectedFilmDto = FilmDto.builder()
                .id(1)
                .name("Valid Film")
                .description("Description of the film")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(10)
                .genres(null)
                .mpa(null)
                .build();
    }

    @Test
    void createFilmTest() {
        when(filmService.createFilm(any(Film.class))).thenReturn(expectedFilmDto);

        ResponseEntity<FilmDto> responseEntity = filmController.createFilm(validFilm);

        assertEquals(ResponseEntity.ok().body(expectedFilmDto), responseEntity);
        verify(filmService).createFilm(filmCaptor.capture());
        assertEquals(validFilm, filmCaptor.getValue());
    }


}
