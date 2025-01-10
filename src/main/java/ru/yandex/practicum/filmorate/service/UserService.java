package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    void addFriend(int userId, int userFriendId);

    void removeFriend(int userId, int userFriendId);

    List<UserDto> getCommonFriendsList(int firstUserId, int secondUserId);

    List<UserDto> getFriendsList(int userId);

    UserDto createUser(User user);

    List<UserDto> getUsers();

    UserDto updateUser(User user);

    UserDto getUserById(int id);

}