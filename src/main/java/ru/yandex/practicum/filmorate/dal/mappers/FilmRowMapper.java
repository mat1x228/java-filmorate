package ru.yandex.practicum.filmorate.dal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {

    @Autowired
    @Lazy
    MpaStorage mpaDbStorage;
    @Autowired
    GenreStorage genreDbStorage;


    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
        film.setReleaseDate(releaseDate);
        film.setDuration(rs.getInt("duration"));
        Optional<Mpa> optionalMpa = mpaDbStorage.getMpaById(rs.getInt("mpaid"));
        optionalMpa.ifPresent(film::setMpa);
        List<Genre> genres = genreDbStorage.getGenresByFilmId(film.getId());
        film.setGenres(genres);

        return film;
    }


}
