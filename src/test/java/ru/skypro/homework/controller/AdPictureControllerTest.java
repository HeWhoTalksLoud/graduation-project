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
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.AdPicture;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdPictureRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UsersRepository;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdPictureControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AdPictureRepository adPictureRepository;
    @Autowired
    AdsRepository adsRepository;
    @Autowired
    private PasswordEncoder encoder;

    private User user = new User();
    private Ad ad = new Ad();
    private AdPicture picture = new AdPicture();
    private String pictureId;


    @BeforeEach
    void setUp() {
        final byte[] bytes = {2, 2, 2, 2};
        picture.setData(bytes);
        picture = adPictureRepository.save(picture);
        pictureId = picture.getId().toString();

        user.setEmail("test_username@mail_te.st");
        user.setFirstName("test_first_name");
        user.setLastName("test_last_name");
        user.setRole(Role.USER);
        user.setPassword(encoder.encode("password"));
        user.setPhone("123456");
        user.setImage("/picture/" + pictureId);
        user = usersRepository.save(user);

        ad.setTitle("Test ad title");
        ad.setPublishDateTime(Instant.now());
        ad.setDescription("Test ad description");
        ad.setAuthor(user);
        ad.setPrice(2500);
        ad = adsRepository.save(ad);

    }

    @AfterEach
    void tearDown() {
        adsRepository.deleteAll();
        usersRepository.deleteAll();
        adPictureRepository.deleteAll();
    }

    @Test
    void getPicture() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/picture/" + pictureId))
                .andExpect(status().isOk()).andReturn();
        byte[] bytesReceived = mvcResult.getResponse().getContentAsByteArray();
        final byte[] bytesExpected = {2, 2, 2, 2};
        assertArrayEquals(bytesExpected, bytesReceived);
    }
}