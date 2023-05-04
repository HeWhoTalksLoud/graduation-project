package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class NewPasswordDTO {
    private String currentPassword;
    private String newPassword;
}
