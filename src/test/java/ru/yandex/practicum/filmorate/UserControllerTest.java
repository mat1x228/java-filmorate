package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.interfaces.UserServiceImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTests {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user1 = new User(1, "John Doe", "john.doe@example.com", "johndoe", LocalDate.of(1990, 1, 1));
        user2 = new User(2, "Jane Smith", "jane.smith@example.com", "janesmith", LocalDate.of(1985, 5, 15));
    }

    @Test
    void testGetUsers_whenUsersExist_returnsUsers() {
        when(userServiceImpl.getUsers()).thenReturn(Arrays.asList(user1, user2));

        Collection<User> users = userController.getUsers();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void testAddUser_whenUserIsValid_returnsCreatedUser() {
        when(userServiceImpl.createUser(user1)).thenReturn(user1);

        ResponseEntity<User> responseEntity = userController.addUser(user1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user1, responseEntity.getBody());
    }

    @Test
    void testAddUser_whenUserIsInvalid_throwsResponseStatusException() {
        User invalidUser = new User(0, null, "invalid.email@example.com", "invalid", LocalDate.now());

        when(userServiceImpl.createUser(invalidUser)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userController.addUser(invalidUser));
    }

    @Test
    void testUpdateUser_whenUserNotFound_throwsResponseStatusException() {
        User updatedUser = new User(1, "John Doe Updated", "john.doe@example.com", "johndoeupdated", LocalDate.of(1990, 1, 1));

        when(userServiceImpl.updateUser(updatedUser)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userController.updateUser(updatedUser));
    }
}

