//package ru.yandex.practicum.filmorate;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import ru.yandex.practicum.filmorate.controller.UserController;
//import ru.yandex.practicum.filmorate.model.User;
//import ru.yandex.practicum.filmorate.service.UserServiceImpl;
//import ru.yandex.practicum.filmorate.storage.user.UserStorage;
//
//import java.time.LocalDate;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//class UserControllerTest {
//
//    @Mock
//    private UserServiceImpl userServiceImpl;
//
//    @Mock
//    private UserStorage userStorage;
//
//    @InjectMocks
//    private UserController userController;
//
//    private User user1;
//    private User user2;
//
//    @BeforeEach
//    @SuppressWarnings("resource")
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        Set<Integer> friendsSetForFirstUser = new HashSet<>();
//        Set<Integer> friendsSetForSecondUser = new HashSet<>();
//        user1 = new User(1, "John Doe", "john.doe@example.com", "johndoe", LocalDate.of(1990, 1, 1), friendsSetForFirstUser);
//        user2 = new User(2, "Jane Jane", "jane.smith@example.com", "janesmith", LocalDate.of(1985, 5, 15), friendsSetForSecondUser);
//    }
//
//
//    @Test
//    void getUsersTest() {
//        when(userServiceImpl.getUsers()).thenReturn(Arrays.asList(user1, user2));
//
//        Collection<User> users = userController.getUsers();
//
//        assertEquals(2, users.size());
//        assertTrue(users.contains(user1));
//        assertTrue(users.contains(user2));
//    }
//
//    @Test
//    void addValidUserTest() {
//        when(userServiceImpl.createUser(any(User.class))).thenReturn(user1);
//
//        ResponseEntity<User> responseEntity = userController.addUser(user1);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(user1, responseEntity.getBody());
//    }
//
//    @Test
//    void addFriendTest() {
//        when(userStorage.getUserById(user1.getId())).thenReturn(user1);
//        when(userStorage.getUserById(user2.getId())).thenReturn(user2);
//
//        ResponseEntity<Void> responseEntity = userController.addFriend(user1.getId(), user2.getId());
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void deleteFromFriends() {
//        when(userStorage.getUserById(user1.getId())).thenReturn(user1);
//        when(userStorage.getUserById(user2.getId())).thenReturn(user2);
//
//        ResponseEntity<Void> responseEntity = userController.deleteFriend(user1.getId(), user2.getId());
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void getCommonFriendsListTest() {
//        when(userStorage.getUserById(user1.getId())).thenReturn(user1);
//        when(userStorage.getUserById(user2.getId())).thenReturn(user2);
//
//        userController.addFriend(user1.getId(), user2.getId());
//
//        ResponseEntity<List<User>> responseEntity = userController.getCommonFriendsList(user1.getId(), user2.getId());
//
//        assertNotNull(responseEntity.getBody());
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//}