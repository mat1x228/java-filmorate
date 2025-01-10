package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MpaRepo;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {

    @Autowired
    private final MpaRepo mpaRepo;

    @Override
    public MpaDto getMpaById(Integer id) {
        return mpaRepo.getMpaById(id)
                .map(MpaMapper::mapToMpaDto)
                .orElseThrow(() -> new NotFoundException("Рейтинг с ID: " + id + " не найден"));
    }

    @Override
    public Collection<MpaDto> getAllMpa() {
        return mpaRepo.getAllMpa()
                .stream()
                .map(MpaMapper::mapToMpaDto)
                .sorted(Comparator.comparing(MpaDto::getId))
                .collect(Collectors.toList());
    }
}
