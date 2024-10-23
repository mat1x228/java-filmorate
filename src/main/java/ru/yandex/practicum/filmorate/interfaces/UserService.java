package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getUsers();

    User updateUser(User user);
}