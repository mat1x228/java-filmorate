package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    @NotBlank(message = "Email не может быть пустым.")
    @Email(message = "Неверный формат email")
    private String email;
    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "^[^\\s]+$", message = "Логин не может содержать пробелы.")
    private String login;
    @Nullable
    private String name;
    @NotNull(message = "Дата рождения не может быть пустой")
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
    Set<Friendship> friendships = new HashSet<>();
    private Set<Integer> friends = new HashSet<>();

    public void addFriendUser(Integer id) {
        friends.add(id);
    }

    public void deleteFriendUser(Integer id) {
        friends.remove(id);
    }
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday + '\'' +
                ", friendships=" + friendships +
                '}';
    }
}