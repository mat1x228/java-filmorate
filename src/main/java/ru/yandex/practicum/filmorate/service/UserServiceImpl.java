package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserStorage userStorage;

    public void addFriend(int userId, int userFriendId) {
        log.info("Удаление друга с ID: {} у юзера ID: {}", userFriendId, userId);

        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + userId + " не найден"));
        User userFriend = userStorage.getUserById(userFriendId)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + userFriendId + " не найден"));

        userStorage.addFriend(userId, userFriendId);
        log.info("Пользователь {} подружился с пользователем {}", userFriend.getId(), user.getId());

    }

    public void removeFriend(int userId, int userFriendId) {
        log.info("Удаление друга с ID: {} у юзера ID: {}", userFriendId, userId);

        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + userId + " не найден"));
        User userFriend = userStorage.getUserById(userFriendId)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + userFriendId + " не найден"));

        userStorage.removeFriend(user.getId(), userFriend.getId());
        log.info("Пользователь {} убрал из друзей {}", userFriend.getId(), user.getId());
    }


    public List<UserDto> getCommonFriendsList(int userId, int friendUserId) {
        User firstUser = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + userId + " не найден"));
        User secondUser = userStorage.getUserById(friendUserId)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + friendUserId + " не найден"));

        log.info("Получение списка общих друзей у юзеров {} и {}", userId, friendUserId);

        List<User> firstUserFriends = userStorage.getFriendsList(userId);
        List<User> secondUserFriends = userStorage.getFriendsList(friendUserId);

        List<User> commonFriends = firstUserFriends.stream()
                .filter(secondUserFriends::contains)
                .collect(Collectors.toList());

        return commonFriends.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }


    public List<UserDto> getFriendsList(int userId) {
        UserDto user = userStorage.getUserById(userId)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + userId + " не найден"));

        return userStorage.getFriendsList(userId).stream()
                .map(friend -> UserMapper.mapToUserDto(friend))
                .collect(Collectors.toList());
    }


    public UserDto createUser(User user) {
        User userCreated = userStorage.createUser(user);
        if (userCreated != null) {
            return UserMapper.mapToUserDto(userCreated);
        } else {
            log.error("Не удалось создать юзера");
            throw new ValidationException("Не удалось создать юзера");
        }
    }

    public List<UserDto> getUsers() {
        return userStorage.getUsers()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(User user) {
        User userUpdated = userStorage.updateUser(user);
        if (userUpdated != null) {
            return UserMapper.mapToUserDto(user);
        } else {
            log.error("Юзер с ID: {} не найден", user.getId());
            throw new NotFoundException("Юзер с ID: " + user.getId() + " не найден");
        }
    }

    public UserDto getUserById(int id) {
        return userStorage.getUserById(id)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Юзер с ID: " + id + " не найден"));

    }

}
