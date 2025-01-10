package ru.yandex.practicum.filmorate.dal;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class GenreRepo extends BaseRepo<Genre> implements GenreStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM GENRES";
    private static final String FIND_GENRES_BY_FILM_ID_QUERY = "SELECT g.id, g.name FROM filmgenres fg " +
            "INNER JOIN genres g ON fg.genreid = g.id WHERE fg.filmid = ?";

    public GenreRepo(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Genre> getGenres() {
        return findMany(FIND_ALL_QUERY);
    }

    ;

    @Override
    public Optional<Genre> getGenreById(Integer genreId) {
        return findOne(FIND_ALL_QUERY.concat(" WHERE id = ?"), genreId.toString());
    }

    @Override
    public List<Genre> getGenresByFilmId(Integer filmId) {
        return findMany(FIND_GENRES_BY_FILM_ID_QUERY, filmId);
    }


}
