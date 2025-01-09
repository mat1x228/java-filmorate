package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }


    @GetMapping
    public Collection<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<UserDto>> getFriendsList(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.getFriendsList(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<UserDto>> getCommonFriendsList(@PathVariable int id, @PathVariable int otherId) {
        return ResponseEntity.ok().body(userService.getCommonFriendsList(id, otherId));
    }


}