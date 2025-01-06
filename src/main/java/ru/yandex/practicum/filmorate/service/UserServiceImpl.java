package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserStorage userStorage;

    public void addFriend(int userId, int userFriendId) {
        log.info("Добавление друга с ID: {} к юзеру с ID: {}", userFriendId, userId);
        User user = userStorage.getUserById(userId);
        User userFriend = userStorage.getUserById(userFriendId);

        if (user != null & userFriend != null) {
            userFriend.addFriendUser(user.getId());
            log.info("Пользователь {} подружился с {}", userFriend.getId(), user.getId());
            user.addFriendUser(userFriend.getId());
            log.info("Пользователь {} подружился с {}", user.getId(), userFriend.getId());
        } else {
            log.error("Юзер с ID: {} не найден", userId);
            throw new NotFoundException("Юзер с ID: " + userId + " не найден");
        }

    }

    public void removeFriend(int userId, int userFriendId) {
        log.info("Удаление друга с ID: {} у юзера ID: {}", userId, userFriendId);
        User user = userStorage.getUserById(userId);
        User userFriend = userStorage.getUserById(userFriendId);

        if (user != null && userFriend != null) {
            user.deleteFriendUser(userFriend.getId());
            log.info("Пользователь {} убрал из друзей {}", user.getId(), userFriend.getId());
            userFriend.deleteFriendUser(user.getId());
            log.info("Пользователь {} убрал из друзей {}", userFriend.getId(), user.getId());
        } else {
            log.error("Юзер с ID: {} не найден", userId);
            throw new NotFoundException("Юзер с ID: " + userId + " не найден");
        }

    }


    public List<User> getCommonFriendsList(int userId, int friendUserId) {
        List<User> commonFriends = new ArrayList<>();
        User firstUser = userStorage.getUserById(userId);
        User secondUser = userStorage.getUserById(friendUserId);

        for (Integer friendId : firstUser.getFriends()) {
            log.info("Получение списка обших друзей у юзеров {} и {}", userId, friendUserId);
            if (secondUser.getFriends().contains(friendId)) {
                commonFriends.add(userStorage.getUserById(friendId));
            }
        }
        return commonFriends;
    }


    public List<User> getFriendsList(int userId) {
        User user = userStorage.getUserById(userId);

        if (user != null) {
            log.info("Получение списка друзей юзера с ID: {}", userId);
            return userStorage.getUserById(userId).getFriends().stream()
                    .map(f -> userStorage.getUserById(f))
                    .collect(Collectors.toList());
        } else {
            log.error("Юзер с ID: {} не найден", userId);
            throw new NotFoundException("Юзер с ID: " + userId + " не найден");
        }
    }
    public User createUser(User user){
        User userCreated = userStorage.createUser(user);
        if (userCreated != null) {
            return userCreated;
        } else {
            log.error("Не удалось создать юзера");
            throw new ValidationException("Не удалось создать юзера");
        }
    }

    public List<User> getUsers(){
        return userStorage.getUsers();
    }

    public User updateUser(User user){
        User userUpdated = userStorage.updateUser(user);
        if (userUpdated != null) {
            return userUpdated;
        } else {
            log.error("Юзер с ID: {} не найден", user.getId());
            throw new NotFoundException("Юзер с ID: " + user.getId() + " не найден");
        }
    }

    public User getUserById(int id){
        User user = userStorage.getUserById(id);

        if (user != null) {
            return user;
        } else {
            log.error("Юзер с ID: {} не найден", id);
            throw new NotFoundException("Юзер с ID: " + id + " не найден");
        }
    }

}
