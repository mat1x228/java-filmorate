package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.interfaces.UserServiceImpl;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/users")
public class UserController {

    private UserServiceImpl userServiceImpl = new UserServiceImpl();

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        log.info("Создание юзера");
        User userCreated = userServiceImpl.createUser(user);
        if (userCreated != null) {
            log.info("Юзер создан: {}", userCreated);
            log.trace("User name: {}, Email: {}, Login: {}, Birthday: {}",
                    user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
            return ResponseEntity.ok().body(userCreated);
        } else {
            log.error("Юзер не был создан");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userCreated);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.info("Обновление юзера с ID: {}", user.getId());

        User userUpdated = userServiceImpl.updateUser(user);

        if (userUpdated != null) {
            log.info("Юзер обновлен: {}", userUpdated);
            log.trace("Имя пользователя: {}, Емайл пользователя: {}, Логин пользователя: {}, Дата рождения: {}",
                    userUpdated.getName(), userUpdated.getEmail(), userUpdated.getLogin(), userUpdated.getBirthday());
            return ResponseEntity.ok().body(userUpdated);
        } else {
            log.error("Юзер с ID: {} не найден", user.getId());
            User notFoundUser = new User();
            notFoundUser.setId(user.getId());
            notFoundUser.setName("Пользователь не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundUser);
        }
    }


    @GetMapping
    public Collection<User> getUsers() {
        log.info("Получение всех юзеров");
        List<User> users = new ArrayList<>(userServiceImpl.getUsers());
        log.debug("Количество пользователей: " + userServiceImpl.getUsers().size());
        return users;
    }

}