package com.petfinder.service;

import com.petfinder.PetFinderApplication;
import com.petfinder.dao.UserRepository;
import com.petfinder.domain.Advertisement;
import com.petfinder.domain.User;
import com.petfinder.exception.UserStateNotChangedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = PetFinderApplication.class)
public class BanningServiceTest {

    @Spy
    private BanningService banningService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @Before
    public void setUp() throws Exception {
        banningService = new BanningService();
        banningService.userRepository = userRepository;
        banningService.userService = userService;
    }

    // case #20
    @Test
    public void shouldBanUser() throws Exception {
        User moderator = new User();
        moderator.setRole("MODERATOR");
        User user = new User();
        user.setRole("USER");
        user.setLogin("user");
        when(userService.getLoggedUser()).thenReturn(moderator);
        when(userRepository.findOneByLogin(user.getLogin())).thenReturn(user);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        banningService.banUser(user.getLogin());

        verify(userRepository).save(captor.capture());
        assertTrue(captor.getValue().isBanned());
        assertEquals(captor.getValue().getLogin(), user.getLogin());
    }

    // case #21
    @Test
    public void shouldUnbanUser() throws Exception {
        User moderator = new User();
        moderator.setRole("MODERATOR");
        User user = new User();
        user.setRole("USER");
        user.setLogin("user");
        user.setBanned(true);
        when(userService.getLoggedUser()).thenReturn(moderator);
        when(userRepository.findOneByLogin(user.getLogin())).thenReturn(user);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        banningService.unbanUser(user.getLogin());

        verify(userRepository).save(captor.capture());
        assertFalse(captor.getValue().isBanned());
        assertEquals(captor.getValue().getLogin(), user.getLogin());
    }

    // case #22
    @Test(expected = UserStateNotChangedException.class)
    public void shouldNotBanUserWhenAlreadyBanned() throws Exception {
        User moderator = new User();
        moderator.setRole("MODERATOR");
        User user = new User();
        user.setRole("USER");
        user.setLogin("user");
        user.setBanned(true);
        when(userService.getLoggedUser()).thenReturn(moderator);
        when(userRepository.findOneByLogin(user.getLogin())).thenReturn(user);

        banningService.banUser(user.getLogin());
        verifyZeroInteractions(userRepository);
    }

    // case #23
    @Test(expected = UserStateNotChangedException.class)
    public void shouldNotUnbanUserWhenAlreadyBanned() throws Exception {
        User moderator = new User();
        moderator.setRole("MODERATOR");
        User user = new User();
        user.setRole("USER");
        user.setLogin("user");
        user.setBanned(false);
        when(userService.getLoggedUser()).thenReturn(moderator);
        when(userRepository.findOneByLogin(user.getLogin())).thenReturn(user);

        banningService.unbanUser(user.getLogin());
        verifyZeroInteractions(userRepository);
    }

    // case #24
    @Test(expected = SecurityException.class)
    public void shouldNotBanUserWhenNotModerator() throws Exception {
        User notModerator = new User();
        notModerator.setRole("USER");
        User user = new User();
        user.setRole("USER");
        user.setLogin("user");
        user.setBanned(false);
        when(userService.getLoggedUser()).thenReturn(notModerator);
        when(userRepository.findOneByLogin(user.getLogin())).thenReturn(user);

        banningService.banUser(user.getLogin());
        verifyZeroInteractions(userRepository);
    }

    // case #25
    @Test(expected = SecurityException.class)
    public void shouldNotUnbanUserWhenNotModerator() throws Exception {
        User notModerator = new User();
        notModerator.setRole("USER");
        User user = new User();
        user.setRole("USER");
        user.setLogin("user");
        user.setBanned(false);
        when(userService.getLoggedUser()).thenReturn(notModerator);
        when(userRepository.findOneByLogin(user.getLogin())).thenReturn(user);

        banningService.unbanUser(user.getLogin());
        verifyZeroInteractions(userRepository);
    }
}