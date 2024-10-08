package ru.yandex.practicum.filmorate.interfaces;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    private static Map<Integer, User> userStorage = new HashMap<Integer, User>();

    private static final AtomicInteger USER_ID_HOLDER = new AtomicInteger();

    @Override
    public User createUser(User user) {
        final int userId = USER_ID_HOLDER.incrementAndGet();
        user.setId(userId);
        String userName = user.getName() != null && !user.getName().isEmpty() ? user.getName() : user.getLogin();
        user.setName(userName);
        userStorage.put(userId, user);
        return user;
    }


    @Override
    public List<User> getUsers() {
        Collection<User> values = userStorage.values();
        return new ArrayList<>(values);
    }

    @Override
    public User updateUser(User user) {
        if(userStorage.containsKey(user.getId())) {
            Integer userId = user.getId();
            userStorage.remove(user.getId());
            userStorage.put(userId, user);
            return userStorage.get(userId);
        }
        return null;
    }
}