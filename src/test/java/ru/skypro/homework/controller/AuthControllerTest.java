package ru.skypro.homework.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UsersRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder encoder;

    private User user = new User();

    @BeforeEach
    void setUp() {
        user.setEmail("test_username@mail_te.st");
        user.setFirstName("test_first_name");
        user.setLastName("test_last_name");
        user.setRole(Role.USER);
        user.setPassword(encoder.encode("password"));
        user.setPhone("123456");
        user = usersRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    @Test
    void login() throws Exception {
        JSONObject jsonLogin = new JSONObject();
        jsonLogin.put("username", "test_username@mail_te.st");
        jsonLogin.put("password", "password");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLogin.toString()))
                        .andExpect(status().isOk()).andReturn();
    }

    @Test
    void register() throws Exception {
        JSONObject jsonRegister = new JSONObject();
        jsonRegister.put("username", "test2");
        jsonRegister.put("password", "password2");
        jsonRegister.put("firstName", "F");
        jsonRegister.put("lastName", "L");
        jsonRegister.put("phone", "456");
        jsonRegister.put("role", Role.ADMIN);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRegister.toString()))
                .andExpect(status().isOk()).andReturn();

        JSONObject jsonLogin = new JSONObject();
        jsonLogin.put("username", "test2");
        jsonLogin.put("password", "222");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLogin.toString())).andExpect(status().is4xxClientError());

        jsonLogin.put("password", "password2");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLogin.toString()))
                .andExpect(status().isOk());
    }
}