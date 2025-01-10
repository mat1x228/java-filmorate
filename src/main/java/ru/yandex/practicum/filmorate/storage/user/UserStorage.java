package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User createUser(User user);

    List<User> getUsers();

    User updateUser(User user);

    Optional<User> getUserById(int id);

    void addFriend(int userId, int userFriendId);

    void removeFriend(int userId, int userFriendId);

    List<User> getCommonFriendsList(int firstUserId, int secondUserId);

    List<User> getFriendsList(int userId);
}
