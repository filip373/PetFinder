package com.petfinder;

import com.petfinder.PetFinderApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by Filip-PC on 18.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(PetFinderApplication.class)
public class UsersTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void registerUserTest() throws Exception {
        this.mockMvc.perform(post("/register")
                .param("login", "test_login").param("password", "test_password")
                .param("repeatPassword", "test_password").param("email", "test@email.com")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
//                .andExpect(content().contentType(MediaType.TEXT_HTML))
//                .andExpect(jsonPath("$.name").value("Lee"));
    }
}
