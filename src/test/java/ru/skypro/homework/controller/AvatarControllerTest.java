package ru.skypro.homework.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UsersRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AvatarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AvatarRepository avatarRepository;
    @Autowired
    private PasswordEncoder encoder;


    private User user = new User();
    private Avatar avatar = new Avatar();
    private String avatarId;

    @BeforeEach
    void setUp() {
        final byte[] bytes = {1, 1, 1, 1};
        avatar.setData(bytes);
        avatar = avatarRepository.save(avatar);
        avatarId = avatar.getId().toString();

        user.setEmail("test_username@mail_te.st");
        user.setFirstName("test_first_name");
        user.setLastName("test_last_name");
        user.setRole(Role.USER);
        user.setPassword(encoder.encode("password"));
        user.setPhone("123456");
        user.setImage("/avatar/" + avatarId);
        user = usersRepository.save(user);


    }

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
        avatarRepository.deleteAll();
    }

    @Test
    void getAvatar() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/avatar/" + avatarId))
                .andExpect(status().isOk()).andReturn();
        byte[] bytesReceived = mvcResult.getResponse().getContentAsByteArray();
        final byte[] bytesExpected = {1, 1, 1, 1};
        assertArrayEquals(bytesExpected, bytesReceived);
    }
}