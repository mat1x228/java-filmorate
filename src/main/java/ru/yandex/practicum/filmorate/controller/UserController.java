package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;
import ru.yandex.practicum.filmorate.service.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final FilmServiceImpl filmServiceImpl;
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, FilmServiceImpl filmServiceImpl,
                          UserStorage userStorage) {
        this.userServiceImpl = userServiceImpl;
        this.filmServiceImpl = filmServiceImpl;
        this.userStorage = userStorage;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User userCreated = userStorage.createUser(user);
        if (userCreated != null) {
            return ResponseEntity.ok().body(userCreated);
        } else {
            log.error("Не удалось создать юзера");
            throw new ValidationException("Не удалось создать юзера");
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {


        User userUpdated = userStorage.updateUser(user);
        if (userUpdated != null) {
            return ResponseEntity.ok().body(userUpdated);
        } else {
            log.error("Юзер с ID: {} не найден", user.getId());
            throw new NotFoundException("Юзер с ID: " + user.getId() + " не найден");
        }
    }


    @GetMapping
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userStorage.getUserById(id);

        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            log.error("Юзер с ID: {} не найден", id);
            throw new NotFoundException("Юзер с ID: " + id + " не найден");
        }
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable int id, @PathVariable int friendId) {
        userServiceImpl.addFriend(id, friendId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userServiceImpl.removeFriend(id, friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/friends")
    public ResponseEntity<List<User>> getFriendsList(@PathVariable int id) {
        return ResponseEntity.ok().body(userServiceImpl.getFriendsList(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonFriendsList(@PathVariable int id, @PathVariable int otherId) {
        return ResponseEntity.ok().body(userServiceImpl.getCommonFriendsList(id, otherId));
    }


}