package com.petfinder;

import com.petfinder.dao.UserRepository;
import com.petfinder.domain.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Filip-PC on 18.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PetFinderApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations = "classpath:test.properties")
public class UsersTests {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        userRepository.deleteAll();
    }

    @Test // case 1
    public void shouldAddRegisteredUserToDatabase() throws Exception {
        long countBefore = userRepository.count();
        this.mockMvc.perform(post("/register").with(csrf())
                .param("login", "test_login").param("password", "test_password")
                .param("repeatPassword", "test_password").param("email", "test@email.com"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(Matchers.containsString("User 'test_login' has been registered successfully.")));
        assertTrue(userRepository.existsByEmail("test@email.com"));
        assertThat(userRepository.findOneByEmail("test@email.com")).matches(user ->
                user.getLogin().equals("test_login") &&
                        new BCryptPasswordEncoder().matches("test_password", user.getPassword()));
        assertEquals(countBefore + 1, userRepository.count());
    }

    @Test // case 2
    public void shouldNotAddUser_IfLoginAlreadyTaken() throws Exception {
        userRepository.save(new User("some_login", "email@email.com", "some_password"));
        long countBefore = userRepository.count();
        this.mockMvc.perform(post("/register").with(csrf())
                .param("login", "some_login").param("password", "test_password")
                .param("repeatPassword", "test_password").param("email", "test@email.com"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(Matchers.containsString("User 'some_login' already exists.")));
        assertEquals(countBefore, userRepository.count());
    }

    @Test // case 3
    public void shouldNotAddUser_IfEmailAlreadyTaken() throws Exception {
        userRepository.save(new User("some_login", "same@email.com", "some_password"));
        long countBefore = userRepository.count();
        this.mockMvc.perform(post("/register").with(csrf())
                .param("login", "another_login").param("password", "test_password")
                .param("repeatPassword", "test_password").param("email", "same@email.com"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(Matchers.containsString("Email 'same@email.com' is already used.")));
        assertEquals(countBefore, userRepository.count());
    }

    @Test // case 5
    public void shouldLogIn() throws Exception {
        userRepository.save(
                new User("some_login", "test@email.com", new BCryptPasswordEncoder().encode("some_password")));
        this.mockMvc.perform(post("/login").with(csrf()).param("username", "some_login")
                .param("password", "some_password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());
    }
}
