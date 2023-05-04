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
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.security.CustomUserDetailsService;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private Authentication auth;
    private Ad ad = new Ad();
    private Comment comment = new Comment();
    private User user = new User();
    private String adId;
    private String commentId;

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
        ad.setPrice(1500);
        ad = adsRepository.save(ad);
        adId = ad.getId().toString();

        comment.setText("Test comment text");
        comment.setCreationDateTime(Instant.now());
        comment.setAuthor(user);
        comment.setAd(ad);
        comment = commentsRepository.save(comment);
        commentId = comment.getId().toString();


        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        auth = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @AfterEach
    void tearDown() {
        commentsRepository.deleteAll();
        adsRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    void getAdComments() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/ads/" + adId + "/comments")
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        String count = parser.object().get("count").toString();
        assertEquals(count, "1");
    }

    @Test
    void addComment() throws Exception {
        JSONObject jsonComment = new JSONObject();
        jsonComment.put("text", "text comment text");
        MvcResult mvcResult = mockMvc.perform(post("/ads/" + adId + "/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonComment.toString())
                .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        String text = parser.object().get("text").toString();
        assertEquals(text, "text comment text");

        mvcResult = mockMvc.perform(get("/ads/" + adId + "/comments")
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        String count = parser.object().get("count").toString();
        assertEquals(count, "2");
    }

    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(delete("/ads/" + adId + "/comments/" + commentId)
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/ads/" + adId + "/comments")
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        String count = parser.object().get("count").toString();
        assertEquals(count, "0");
    }

    @Test
    void updateComment() throws Exception {
        JSONObject jsonComment = new JSONObject();
        jsonComment.put("text", "new text");
        MvcResult mvcResult = mockMvc.perform(patch("/ads/" + adId + "/comments/" + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComment.toString())
                        .with(authentication(auth)))
                .andExpect(status().isOk()).andReturn();
        JSONParser parser = new JSONParser(mvcResult.getResponse().getContentAsString());
        String text = parser.object().get("text").toString();
        assertEquals(text, "new text");

        mockMvc.perform(patch("/ads/" + adId + "/comments/" + commentId + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComment.toString())
                        .with(authentication(auth)))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(patch("/ads/" + adId + "/comments/" + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComment.toString()))
                .andExpect(status().is4xxClientError());

        user.setRole(Role.ADMIN);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        auth = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        mockMvc.perform(patch("/ads/" + adId + "/comments/" + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComment.toString())
                        .with(authentication(auth)))
                .andExpect(status().isOk());
    }
}