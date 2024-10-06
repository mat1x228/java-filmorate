package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    @NotBlank(message = "Email не может быть пустым.")
    @Email(message = "Неверный формат email")
    private String email;
    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "^[^\\s]+$", message = "Логин не может содержать пробелы.")
    private String login;
    private String name;
    @NotNull(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
