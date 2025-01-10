package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User validUser;
    private UserDto expectedUserDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validUser = User.builder()
                .id(1)
                .email("john.doe@example.com")
                .login("john_doe")
                .name("John Doe")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        expectedUserDto = UserDto.builder()
                .id(1)
                .email("john.doe@example.com")
                .login("john_doe")
                .name("John Doe")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    void createUserTest() {
        when(userService.createUser(any(User.class))).thenReturn(expectedUserDto);

        ResponseEntity<UserDto> responseEntity = userController.addUser(validUser);

        assertEquals(ResponseEntity.ok(expectedUserDto), responseEntity);

        assertEquals(ResponseEntity.ok().body(expectedUserDto), responseEntity);
        verify(userService).createUser(userCaptor.capture());
        assertEquals(validUser, userCaptor.getValue());
    }
}
