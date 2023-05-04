package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UsersRepository;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    UsersRepository usersRepository;
    @Mock
    AvatarRepository avatarRepository;
    @InjectMocks
    UsersService usersService;

    @Test
    void updateUser() {
        UserDTO dto = new UserDTO();
        assertThrows(NotFoundException.class, () -> usersService.updateUser(dto));

    }

    @Test
    void setAvatar() {
        MultipartFile image = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IllegalStateException {

            }
        };
        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(avatarRepository.save(any(Avatar.class))).thenReturn(new Avatar() {
            @Override
            public Long getId() {
                return 1L;
            }
        });

        assertDoesNotThrow(() -> usersService.setAvatar(1L, image));
    }

    @Test
    void getAuthorisedUser() {
        assertThrows(NotFoundException.class, () -> usersService.getAuthorisedUser(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return "test";
            }

            @Override
            public Object getDetails() {
                return "test";
            }

            @Override
            public Object getPrincipal() {
                return "test";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "test";
            }
        }));
    }

    @Test
    void setPassword() {
        NewPasswordDTO dto = new NewPasswordDTO();
        assertThrows(NotFoundException.class, () -> usersService.setPassword(1L, dto));
    }
}