package ru.yandex.practicum.filmorate.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final Map<Integer, User> userStorage = new HashMap<>();

    private static final AtomicInteger USER_ID_HOLDER = new AtomicInteger();

    @Override
    public User createUser(User user) {
        final int userId = USER_ID_HOLDER.incrementAndGet();
        user.setId(userId);

        String userName;
        if (user.getName() != null && !user.getName().isEmpty()) {
            userName = user.getName();
        } else {
            userName = user.getLogin();
        }

        user.setName(userName);
        userStorage.put(userId, user);
        log.info("Юзер создан: {}", user);
        log.trace("User name: {}, Email: {}, Login: {}, Birthday: {}",
                user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        return user;
    }


    @Override
    public List<User> getUsers() {
        Collection<User> values = userStorage.values();
        log.debug("Количество пользователей: " + values.size());
        return new ArrayList<>(values);
    }

    @Override
    public User updateUser(User user) {
        if (userStorage.containsKey(user.getId()) && user.getId() > 0) {
            Integer userId = user.getId();
            userStorage.remove(user.getId());
            userStorage.put(userId, user);
            log.info("Юзер обновлен: {}", user);
            log.trace("Имя пользователя: {}, Емайл пользователя: {}, Логин пользователя: {}, Дата рождения: {}",
                    user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
            return userStorage.get(userId);
        }
        return null;
    }
}
