package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.RegisterReqDTO;
import ru.skypro.homework.security.CustomUserDetailsService;

@Service
@Transactional
public class AuthService {

    private final CustomUserDetailsService manager;
    private final PasswordEncoder encoder;

    public AuthService(CustomUserDetailsService manager) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
    }


    public boolean login(String userName, String password) {
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    public boolean register(RegisterReqDTO dto) {
        if(dto.getUsername() == null || dto.getUsername().isBlank()
                || dto.getFirstName() == null || dto.getFirstName().isBlank()
                || dto.getLastName() == null || dto.getLastName().isBlank()
                || dto.getPhone() == null || dto.getPhone().isBlank()
                || dto.getPassword() == null || dto.getPassword().isBlank()) throw new RuntimeException("Не указан" +
                "нужный параметр");
        
        manager.createUser(dto);
        return true;
    }

}
