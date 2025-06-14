package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.UserStorage; // Keep as it's used in tests

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import ru.yandex.practicum.filmorate.exception.NotFoundException; // Added for new tests


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserStorage userStorage; // This mock might be problematic if UserServiceImpl is also mocked and uses it.
                                     // Typically, you mock the service and test the controller's interaction with the service.
                                     // Or, if testing service layer, mock the storage.
                                     // For controller tests, direct interaction with userStorage mock is unusual if userServiceImpl is the primary collaborator.
                                     // However, I will keep it as per the original structure and the subtask instructions.

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); // Removed
        Set<Integer> friendsSetForFirstUser = new HashSet<>();
        Set<Integer> friendsSetForSecondUser = new HashSet<>();
        user1 = new User(1, "John Doe", "john.doe@example.com", "johndoe", LocalDate.of(1990, 1, 1), friendsSetForFirstUser);
        user2 = new User(2, "Jane Jane", "jane.smith@example.com", "janesmith", LocalDate.of(1985, 5, 15), friendsSetForSecondUser);
    }


    @Test
    void getUsersTest() {
        when(userServiceImpl.getUsers()).thenReturn(Arrays.asList(user1, user2));

        Collection<User> users = userController.getUsers();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void addValidUserTest() {
        when(userServiceImpl.createUser(any(User.class))).thenReturn(user1);

        ResponseEntity<User> responseEntity = userController.addUser(user1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user1, responseEntity.getBody());
    }

    @Test
    void addFriendTest() {
        // The controller delegates to userServiceImpl.addFriend.
        // So, we should verify that userServiceImpl.addFriend is called.
        // No need to mock userStorage.getUserById for this controller test if userServiceImpl handles that.
        // If userServiceImpl.addFriend throws an exception, the test will fail.
        // doNothing().when(userServiceImpl).addFriend(user1.getId(), user2.getId()); // if addFriend is void

        ResponseEntity<Void> responseEntity = userController.addFriend(user1.getId(), user2.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userServiceImpl, times(1)).addFriend(user1.getId(), user2.getId());
    }

    @Test
    void deleteFromFriends() {
        // Similar to addFriendTest, controller delegates to userServiceImpl.removeFriend
        // doNothing().when(userServiceImpl).removeFriend(user1.getId(), user2.getId());

        ResponseEntity<Void> responseEntity = userController.deleteFriend(user1.getId(), user2.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userServiceImpl, times(1)).removeFriend(user1.getId(), user2.getId());
    }

    @Test
    void getCommonFriendsListTest() {
        // Controller delegates to userServiceImpl.getCommonFriendsList
        List<User> commonFriends = new ArrayList<>(); // Example list
        when(userServiceImpl.getCommonFriendsList(user1.getId(), user2.getId())).thenReturn(commonFriends);

        // userController.addFriend(user1.getId(), user2.getId()); // This call makes the test dependent on addFriend working
                                                              // and also calls userServiceImpl.addFriend, which might not be intended here.
                                                              // It's better to test getCommonFriendsList in isolation.

        ResponseEntity<List<User>> responseEntity = userController.getCommonFriendsList(user1.getId(), user2.getId());

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commonFriends, responseEntity.getBody());
        verify(userServiceImpl, times(1)).getCommonFriendsList(user1.getId(), user2.getId());
    }

    @Test
    void testUpdateUser_Success() {
        User userToUpdate = new User(1, "Updated User", "update@example.com", "updatedlogin", LocalDate.of(1990, 1, 1), new HashSet<>());
        when(userServiceImpl.updateUser(any(User.class))).thenReturn(userToUpdate);

        ResponseEntity<User> response = userController.updateUser(userToUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userToUpdate, response.getBody());
        verify(userServiceImpl, times(1)).updateUser(userToUpdate);
    }

    @Test
    void testUpdateUser_NotFound() {
        User userToUpdate = new User(99, "Unknown User", "unknown@example.com", "unknownlogin", LocalDate.of(1990, 1, 1), new HashSet<>());
        when(userServiceImpl.updateUser(any(User.class))).thenThrow(new NotFoundException("User not found"));

        assertThrows(NotFoundException.class, () -> {
            userController.updateUser(userToUpdate);
        });
        verify(userServiceImpl, times(1)).updateUser(userToUpdate);
    }

    @Test
    void testGetUserById_Success() {
        // user1 is initialized in @BeforeEach
        when(userServiceImpl.getUserById(user1.getId())).thenReturn(user1);

        ResponseEntity<User> response = userController.getUserById(user1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user1, response.getBody());
        verify(userServiceImpl, times(1)).getUserById(user1.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userServiceImpl.getUserById(99)).thenThrow(new NotFoundException("User not found"));

        assertThrows(NotFoundException.class, () -> {
            userController.getUserById(99);
        });
        verify(userServiceImpl, times(1)).getUserById(99);
    }

    @Test
    void testGetFriendsList_Success() {
        // user1 is initialized in @BeforeEach
        List<User> friends = Collections.singletonList(user2); // user2 also from @BeforeEach
        when(userServiceImpl.getFriendsList(user1.getId())).thenReturn(friends);

        ResponseEntity<List<User>> response = userController.getFriendsList(user1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(friends, response.getBody());
        verify(userServiceImpl, times(1)).getFriendsList(user1.getId());
    }

    @Test
    void testGetFriendsList_UserNotFound() {
        when(userServiceImpl.getFriendsList(99)).thenThrow(new NotFoundException("User not found"));

        assertThrows(NotFoundException.class, () -> {
            userController.getFriendsList(99);
        });
        verify(userServiceImpl, times(1)).getFriendsList(99);
    }
}