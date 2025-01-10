package ru.yandex.practicum.filmorate.dal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.Optional;


@Repository
@Primary
public class FilmRepo extends BaseRepo<Film> implements FilmStorage {


    private static final String GET_ALL_QUERY = "SELECT * FROM films";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String CREATE_QUERY = "INSERT INTO films (name, description, releaseDate, duration, mpaid)\n" +
            "VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE FILMS SET name = ?, description = ?, releaseDate = ?, duration = ? WHERE id = ?";
    private static final String INSERT_LIKE_QUERY = "INSERT INTO likes (filmid, userid) VALUES (?, ?)";
    private static final String DELETE_LIKE_QUERY = "DELETE FROM likes WHERE filmid = ? AND userid = ?";

    private static final String FIND_BEST_QUERY = "SELECT f.id, f.name, f.description, f.releaseDate, f.duration, f.mpaid, COUNT(l.userid) AS like_count \n" +
            "FROM films f\n" +
            "LEFT JOIN likes l ON f.id = l.filmid\n" +
            "GROUP BY f.id\n" +
            "ORDER BY like_count DESC\n" +
            "LIMIT ?\n";


    public FilmRepo(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }


    @Override
    public Film createFilm(Film film) {
        System.out.println(film);
        Integer mpaId = null;
        if (film.getMpa() != null & isMpaExists(film.getMpa().getId())) {
            mpaId = film.getMpa().getId();
        } else {
            throw new ValidationException("MPA с ID " + mpaId + " не найден.");
        }


        int id = insert(CREATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                mpaId
        );
        film.setId(id);

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            String queryFilmGenres = "INSERT INTO filmgenres (filmid, genreid) VALUES (?, ?)";
            for (Genre genre : film.getGenres()) {
                update(queryFilmGenres, id, genre.getId());
            }
        }

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
        update(INSERT_LIKE_QUERY, filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        delete(DELETE_LIKE_QUERY, filmId, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(int count) {
        return findMany(FIND_BEST_QUERY, count);
    }

    private boolean isMpaExists(Integer mpaId) {
        String query = "SELECT COUNT(*) FROM mpa WHERE id = ?";
        Integer count = jdbc.queryForObject(query, new Object[]{mpaId}, Integer.class);
        return count != null && count > 0;
    }

}
