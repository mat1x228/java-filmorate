package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotation.validation.ReleaseDate;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext context) {
        if (releaseDate == null) {
            return true;
        }
        LocalDate minDate = LocalDate.of(1895, 12, 28);
        return !releaseDate.isBefore(minDate);
    }
}