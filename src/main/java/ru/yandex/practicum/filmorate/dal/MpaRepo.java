package ru.yandex.practicum.filmorate.dal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.BaseRepo;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;
import java.util.Optional;

@Repository("mpaRepo")
@Qualifier("mpaRepo")
@Primary
public class MpaRepo extends BaseRepo implements MpaStorage {
    public MpaRepo(JdbcTemplate jdbc, RowMapper mapper) {
        super(jdbc, mapper);
    }

    private static final String FIND_ALL_QUERY = "SELECT * FROM mpa";

    @Override
    public Optional<Mpa> getMpaById(int id) {
        return findOne(FIND_ALL_QUERY.concat(" WHERE ID = ?"), id);
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return findMany(FIND_ALL_QUERY);
    }


}
