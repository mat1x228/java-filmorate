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
<<<<<<< HEAD
        String userName = user.getName() != null && !user.getName().isEmpty() ? user.getName() : user.getLogin();
        user.setName(userName);
        userStorage.put(userId, user);
        return user;
    }


=======
        if (user.getName() == null || user.getName().isEmpty()){
            user.setName(user.getLogin());
        } else {
            userStorage.put(userId, user);
        }
        return userStorage.get(userId);
    }

>>>>>>> main
    @Override
    public List<User> getUsers() {
        Collection<User> values = userStorage.values();
        return new ArrayList<>(values);
    }

    @Override
    public User updateUser(User user) {
<<<<<<< HEAD
        if (userStorage.containsKey(user.getId())) {
=======
        if(userStorage.containsKey(user.getId())) {
>>>>>>> main
            Integer userId = user.getId();
            userStorage.remove(user.getId());
            userStorage.put(userId, user);
            return userStorage.get(userId);
        }
        return null;
    }
}