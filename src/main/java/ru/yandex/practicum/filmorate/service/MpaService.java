package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaService {
    MpaDto getMpaById(Integer id);

    Collection<MpaDto> getAllMpa();

}
