package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ValidationException extends RuntimeException {

    private final int code;
    private final String userMessage;
    private final String techMessage;
    private final HttpStatus httpStatus;

}