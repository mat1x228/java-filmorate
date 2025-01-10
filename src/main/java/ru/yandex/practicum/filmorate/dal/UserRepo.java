package ru.yandex.practicum.filmorate.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.BaseRepo;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Repository("userRepo")
@Qualifier("userRepo")
@Slf4j
@Component
@Primary
public class UserRepo extends BaseRepo<User> implements UserStorage {

    private static final String GET_ALL_QUERY = "SELECT * FROM users";

    private static final String GET_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String CREATE_QUERY = "INSERT INTO users (email, login, name, birthday) " +
            "VALUES (?,?,?,?)";
    private static final String FIND_COMMON_FRIENDS_QUERY = "SELECT * FROM users WHERE id IN (SELECT friendid FROM friends WHERE userid = ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, login = ?, name =?, birthday = ? WHERE id = ?";

    private static final String ADD_FRIEND_QUERY = "INSERT INTO friends (userid, friendid) VALUES (?, ?)";

    private static final String FIND_FRIENDS_BY_ID_QUERY =
            "SELECT u.id, u.email, u.login, u.name, u.birthday " +
                    "FROM friends f " +
                    "JOIN users u ON f.friendid = u.id " +
                    "WHERE f.userid = ?";

    private static final String DELETE_FRIEND_QUERY = "DELETE FROM friends WHERE userid = ? AND friendid = ?";


    public UserRepo(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }


    @Override
    public User createUser(User user) {
        System.out.println(user);
        int id = insert(CREATE_QUERY, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        user.setId(id);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return findMany(GET_ALL_QUERY);
    }

    @Override
    public User updateUser(User user) {
        update(UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return findOne(GET_BY_ID_QUERY, id);
    }

    @Override
    public void addFriend(int userId, int userFriendId) {
        insertObjects(ADD_FRIEND_QUERY, userId, userFriendId);
    }

    @Override
    public void removeFriend(int userId, int userFriendId) {
        delete(DELETE_FRIEND_QUERY, userId, userFriendId);
    }

    @Override
    public List<User> getCommonFriendsList(int firstUserId, int secondUserId) {
        return findMany(FIND_COMMON_FRIENDS_QUERY, firstUserId, secondUserId);
    }

    @Override
    public List<User> getFriendsList(int userId) {
        return findMany(FIND_FRIENDS_BY_ID_QUERY, userId);
    }
}
