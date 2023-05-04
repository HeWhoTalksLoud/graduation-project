package ru.skypro.homework.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.security.CustomUserDetailsService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder encoder;

    private Authentication auth;
    private User user = new User();
    private final MockPart imageFile
            = new MockPart("image", "avatar", "avatar".getBytes());

    @BeforeEach
    void setUp() {

        user.setEmail("test_username@mail_te.st");
        user.setPassword(encoder.encode("test_password"));
        user.setFirstName("test_first_name");
        user.setLastName("test_last_name");
        user.setPhone("2-12-85-06");
        user.setRole(Role.USER);

        user = usersRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        auth = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @AfterEach
    void tearDown() {
        usersRepository.delete(user);
    }

    @Test
    void setPassword() throws Exception {
        JSONObject jsonNewPassword = new JSONObject();
        jsonNewPassword.put("currentPassword", "test_password");
        jsonNewPassword.put("newPassword", "new_password");
        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNewPassword.toString())
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();

        jsonNewPassword.put("currentPassword", "new_password");
        jsonNewPassword.put("newPassword", "new_password1");
        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNewPassword.toString())
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();

        jsonNewPassword.put("currentPassword", "test");
        jsonNewPassword.put("newPassword", "new_password1");
        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNewPassword.toString())
                        .with(authentication(auth)))
                .andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void getAuthorisedUserInfo() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/users/me")
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        String email = parser.object().get("email").toString();
        assertEquals(email, "test_username@mail_te.st");
    }

    @Test
    void setAuthorisedUserInfo() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("id", user.getId());
        jsonUser.put("email", "test_username@mail_te.st");
        jsonUser.put("firstName", "F");
        jsonUser.put("lastName", "L");
        jsonUser.put("phone", "111");
        jsonUser.put("image", "");

        MvcResult mvcResult = mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser.toString())
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();

        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        Map<String, Object> parserMap = parser.object();
        String firstName = parserMap.get("firstName").toString();
        String lastName = parserMap.get("lastName").toString();
        assertEquals(firstName, "F");
        assertEquals(lastName, "L");
    }

    @Test
    void setAuthorisedUserAvatar() throws Exception {
        mockMvc.perform(patch("/users/me/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.addPart(imageFile);
                            return request;
                        })
                        .with(authentication(auth)))
                .andExpect(status().isOk());
    }
}