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
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.security.CustomUserDetailsService;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private Authentication auth;
    private User user = new User();
    private Ad ad = new Ad();
    private String adId;
    private final MockPart image
            = new MockPart("image", "image", "image".getBytes());


    @BeforeEach
    void setUp() {
        user.setEmail("test_username@mail_te.st");
        user.setFirstName("test_first_name");
        user.setLastName("test_last_name");
        user.setRole(Role.USER);
        user.setPassword(encoder.encode("password"));
        user.setPhone("123456");
        user = usersRepository.save(user);

        ad.setTitle("Test ad title");
        ad.setPublishDateTime(Instant.now());
        ad.setDescription("Test ad description");
        ad.setAuthor(user);
        ad.setPrice(2500);
        ad = adsRepository.save(ad);
        adId = ad.getId().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        auth = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

    }

    @AfterEach
    void tearDown() {
        adsRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    void getAllAds() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/ads").with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        String count = parser.object().get("count").toString();
        assertEquals("1", count);

        adsRepository.deleteAll();
        mockMvc.perform(get("/ads").with(authentication(auth)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void getAdInfo() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/ads/" + adId)
                .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();

        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        Map<String, Object> map = parser.object();
        String price = map.get("price").toString();
        String title = map.get("title").toString();
        String ad_id = map.get("pk").toString();

        assertEquals("2500", price);
        assertEquals("Test ad title", title);
        assertEquals(adId, ad_id);
    }

    @Test
    void deleteAd() throws Exception {
        mockMvc.perform(delete("/ads/" + adId)
                .with(authentication(auth)))
                .andExpect(status().isOk());
        int adsCount = adsRepository.findAll().size();
        assertEquals(0, adsCount);
    }

    @Test
    void updateAd() throws Exception {
        JSONObject jsonAd = new JSONObject();
        jsonAd.put("description", "test description");
        jsonAd.put("title", "test title");
        jsonAd.put("price", "300");

        MvcResult mvcResult = mockMvc.perform(patch("/ads/" + adId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAd.toString())
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();

        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        Map<String, Object> map = parser.object();
        String price = map.get("price").toString();
        String title = map.get("title").toString();
        String ad_id = map.get("pk").toString();

        assertEquals("300", price);
        assertEquals("test title", title);
        assertEquals(adId, ad_id);
    }

    @Test
    void getAuthorisedUserAds() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/ads/me")
                .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();

        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        Map<String, Object> map = parser.object();
        String count = map.get("count").toString();

        assertEquals("1", count);
    }

    @Test
    void updateAdImage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch("/ads/" + adId + "/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.addPart(image);
                            return request;
                        })
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        byte[] bytes = mvcResult.getResponse().getContentAsByteArray();
        assertArrayEquals("image".getBytes(), bytes);
    }
}