package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.BaseRepo;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.Optional;


@Repository("filmDbStorage")
@Qualifier("filmDbStorage")
@Primary
public class FilmDbStorage extends BaseRepo<Film> implements FilmStorage {

    private static final String GET_ALL_QUERY = "SELECT * FROM films";

    private static final String GET_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String CREATE_QUERY = "INSERT INTO films (name, description, releaseDate, duration) " +
            "VALUES (?,?,?,?)";


    private static final String UPDATE_QUERY = "UPDATE FILMS SET name = ?, description = ?, releaseDate = ?, duration = ? WHERE id = ?";


    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }


//    @Override
//    public Film createFilm(Film film) {
//        Long id = insert(CREATE_QUERY,
//                film.getName(),
//                film.getDescription(),
//                film.getReleaseDate(),
//                film.getDuration()
//        );
//        film.setId(id.intValue());
//
//        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
//            String queryGenres = "INSERT INTO genres (id, name) VALUES (?, ?)";
//            for (Genre genre : film.getGenres()) {
//                insertObjects(queryGenres, genre.getId(), genre.getName());
//            }
//
//            String queryFilmGenres = "INSERT INTO filmgenres (filmid, genreid) VALUES (?, ?)";
//            for (Genre genre : film.getGenres()) {
//                update(queryFilmGenres, film.getId(), genre.getId());
//            }
//        }
//
//        return film;
//    }

    @Override
    public Film createFilm(Film film) {

        Long id = insert(CREATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration()
        );
        film.setId(id.intValue());

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            String queryGenres = "INSERT INTO genres (id, name) VALUES (?, ?)";
            for (Genre genre : film.getGenres()) {
                insertObjects(queryGenres, genre.getId(), genre.getName());
            }

            String queryFilmGenres = "INSERT INTO filmgenres (filmid, genreid) VALUES (?, ?)";
            for (Genre genre : film.getGenres()) {
                update(queryFilmGenres, film.getId(), genre.getId());
            }
        }
                String insertMpaQuery = "INSERT INTO mpa (id, name) VALUES (?, ?)";
                insertObjects(insertMpaQuery, film.getMpa().getId(), film.getMpa().getName());

            String updateFilmMpaQuery = "UPDATE films SET mpaid = ? WHERE id = ?";
            update(updateFilmMpaQuery, film.getMpa().getId(), film.getId());


        return film;
    }


    @Override
    public List<Film> getFilms() {
        return findMany(GET_ALL_QUERY);
    }

    @Override
    public Film updateFilm(Film film) {
        update(UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId()
        );
        return film;
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        return findOne(GET_BY_ID_QUERY, id);
    }

    @Override
    public void addLike(int filmId, int userId) {


    }

    @Override
    public void deleteLike(int filmId, int userId) {

    }
}
