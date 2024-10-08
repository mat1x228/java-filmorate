package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.interfaces.UserServiceImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTests {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    @SuppressWarnings("resource")
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        when(userServiceImpl.createUser(any(User.class))).thenReturn(user1);

        ResponseEntity<User> responseEntity = userController.addUser(user1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user1, responseEntity.getBody());
    }


}