package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

public class MpaMapper {

    public static MpaDto mapToMpaDto(Mpa mpa) {
        return MpaDto.builder()
                .id(mpa.getId())
                .rating(mpa.getName())
                .build();
    }
}
