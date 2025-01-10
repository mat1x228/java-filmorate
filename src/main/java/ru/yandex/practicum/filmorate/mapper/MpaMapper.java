package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

public class MpaMapper {

    public static MpaDto mapToMpaDto(Mpa mpa) {
        if (mpa == null) {
            return null;
        }
        return MpaDto.builder()
                .id(mpa.getId())
                .name(mpa.getName())
                .build();
    }
}
