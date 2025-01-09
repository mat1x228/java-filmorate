package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friendship {

    private Integer requesterId;
    private Integer responderId;
    boolean isConfirmed;

}
