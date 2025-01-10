package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreRepo;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    @Autowired
    private final GenreRepo genreRepo;


    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepo.getGenres().stream()
                .map(GenreMapper::mapToGenreDto)
                .sorted(Comparator.comparing(GenreDto::getId))
                .collect(Collectors.toList());
    }


    @Override
    public GenreDto getGenreById(Integer genreId) {
        return genreRepo.getGenreById(genreId)
                .map(GenreMapper::mapToGenreDto)
                .orElseThrow(() -> new NotFoundException("Жанр с ID: " + genreId + " не найден"));
    }
}
