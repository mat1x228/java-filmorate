package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.interfaces.UserServiceImpl;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        log.info("Создание юзера");
        User userCreated = userServiceImpl.createUser(user);
        if (userCreated != null) {
            return ResponseEntity.ok().body(userCreated);
        } else {
            log.error("Не удалось создать юзера");
            throw new ValidationException("Не удалось создать юзера");
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.info("Обновление юзера с ID: {}", user.getId());

        User userUpdated = userServiceImpl.updateUser(user);

        if (userUpdated != null) {
            return ResponseEntity.ok().body(userUpdated);
        } else {
            log.error("Юзер с ID: {} не найден", user.getId());
            throw new NotFoundException("Юзер с ID: " + user.getId() + " не найден");
        }
    }


    @GetMapping
    public Collection<User> getUsers() {
        log.info("Получение всех юзеров");
        return userServiceImpl.getUsers();
    }

}