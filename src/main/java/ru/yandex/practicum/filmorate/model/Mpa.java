package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mpa {
    Integer id;
    String name;
}
