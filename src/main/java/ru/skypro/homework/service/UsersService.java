package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.security.CustomUserDetails;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final AvatarRepository avatarRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = usersRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Пользователь " + userDTO.getEmail() + " не найден"));
        User userToSave = userDTO.toUser();
        userToSave.setPassword(existingUser.getPassword());
        userToSave.setRole(existingUser.getRole());
        return UserDTO.fromUser(usersRepository.save(userToSave));
    }

    public void setAvatar(Long userId, MultipartFile image) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с ид. " + userId));
        user.setImage(saveAvatar(image));
        usersRepository.save(user);
    }

    private String saveAvatar(MultipartFile image) {
        Avatar avatar = new Avatar();
        try {
            byte[] bytes = image.getBytes();
            avatar.setData(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Avatar savedAvatar = avatarRepository.save(avatar);

        return "/avatar/" + savedAvatar.getId().toString();
    }

    public UserDTO getAuthorisedUser(Authentication authentication) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        if (user == null) {
            throw new NotFoundException("Пользователь " + authentication.getName() + " не найден");
        }

        return UserDTO.fromUser(user);
    }

    public void setPassword(Long userId, NewPasswordDTO newPasswordDTO) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид." + userId + " не найден"));
        if (!passwordEncoder.matches(newPasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new ForbiddenException();
        }
        user.setPassword(passwordEncoder.encode(newPasswordDTO.getNewPassword()));
        usersRepository.save(user);

    }
}
