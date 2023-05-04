package ru.skypro.homework.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReqDTO;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));

        return new CustomUserDetails(user);

    }

    public void createUser(RegisterReqDTO registerReq) {
        String email = registerReq.getUsername();
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new ForbiddenException("Пользователь " + email + " уже существует");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        user.setRole(Role.USER);
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setPhone(registerReq.getPhone());
        usersRepository.save(user);
    }


}
