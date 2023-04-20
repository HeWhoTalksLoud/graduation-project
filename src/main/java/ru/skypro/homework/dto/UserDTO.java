package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.User;

@Data
public class UserDTO {
    // id пользователя
    private Long id;
    // Логин пользователя
    private String email;
    // Имя пользователя
    private String firstName;
    // Фамлия пользователя
    private String lastName;
    // Телефон пользователя
    private String phone;
    // Ссылка на аватар пользователя
    private String image;

    public User toUser() {
        User user = new User();
        user.setId(getId());
        user.setEmail(getEmail());
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setPhone(getPhone());
        user.setImage(getImage());
        return user;
    }

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setImage(user.getImage());
        return dto;
    }
}