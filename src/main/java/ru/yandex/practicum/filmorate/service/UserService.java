package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    void addFriend(int userId, int userFriendId);

    void removeFriend(int userId, int userFriendId);

    List<User> getCommonFriendsList(int firstUserId, int secondUserId);

    List<User> getFriendsList(int userId);

}