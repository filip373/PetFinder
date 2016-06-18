package com.petfinder;

import com.petfinder.dao.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Filip-PC on 18.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(PetFinderApplication.class)
public class UsersTests {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    // case 1
    public void shouldAddRegisteredUserToDatabase() throws Exception {
        this.mockMvc.perform(post("/register")
                .param("login", "test_login").param("password", "test_password")
                .param("repeatPassword", "test_password").param("email", "test@email.com")
                .accept("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content()
                        .string(Matchers.containsString("User 'test_login' has been registered successfully.")));
        assertTrue(userRepository.existsByEmail("test@email.com"));
        assertThat(userRepository.findOneByEmail("test@email.com")).matches(user ->
                user.getLogin().equals("test_login") &&
                        new BCryptPasswordEncoder().matches("test_password", user.getPassword()));
    }
}
