package org.server.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.server.dao.model.user.User;
import org.server.dao.repositories.UsersRepository;
import org.server.domain.errors.DuplicatedUsernameOrPasswordException;
import org.server.domain.errors.UserNotFoundException;
import org.server.security.jwt.JWTService;
import org.server.security.jwt.Token;
import org.server.ui.model.AuthenticationUser;
import org.server.utils.MailComponent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock private JWTService jwtService;
    @Mock private UserDetailsService userDetailsService;
    @Mock private UsersRepository usersRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MailComponent mailComponent;
    @Mock private UserDetails userDetails;

    @InjectMocks private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateToken_shouldReturnToken() {
        String email = "test@example.com";
        Token token = new Token("fake-jwt-token","fake-jwt-token");

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtService.generateToken(anyMap(), eq(userDetails))).thenReturn(token);

        Token result = authenticationService.generateToken(email);

        assertNotNull(result);
        assertEquals("fake-jwt-token", result.getAccess());
        assertEquals("fake-jwt-token", result.getRefresh());
    }

    @Test
    void register_shouldSaveUserAndSendEmail() {
        AuthenticationUser authUser = new AuthenticationUser("user", "pass", "user@example.com");
        when(passwordEncoder.encode("pass")).thenReturn("encoded-pass");

        authenticationService.register(authUser);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(usersRepository).save(userCaptor.capture());
        verify(mailComponent).sendVerificationEmail(eq("user@example.com"), eq("user"), contains("https://"));

        assertEquals("user", userCaptor.getValue().getUsername());
        assertEquals("encoded-pass", userCaptor.getValue().getPassword());
        assertEquals("user@example.com", userCaptor.getValue().getEmail());
        assertNotNull(userCaptor.getValue().getCode());
    }

    @Test
    void register_shouldThrowExceptionOnDuplicateUser() {
        AuthenticationUser authUser = new AuthenticationUser("user", "pass", "user@example.com");

        when(passwordEncoder.encode("pass")).thenReturn("encoded-pass");
        doThrow(DataIntegrityViolationException.class).when(usersRepository).save(any(User.class));

        assertThrows(DuplicatedUsernameOrPasswordException.class, () -> {
            authenticationService.register(authUser);
        });
    }

    @Test
    void isTokenValid_shouldReturnTrueIfValid() {
        String token = "valid.token";
        when(jwtService.extractUsername(token)).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        boolean result = authenticationService.isTokenValid(token);

        assertTrue(result);
    }

    @Test
    void extractUsername_shouldReturnEmail() {
        String token = "token";
        when(jwtService.extractUsername(token)).thenReturn("user@example.com");

        String result = authenticationService.extractUsername(token);

        assertEquals("user@example.com", result);
    }

    @Test
    void confirmUser_shouldEnableUser() {
        String code = "abc123";
        User user = new User();
        user.setEnabled(false);

        when(usersRepository.findUserByCode(code)).thenReturn(Optional.of(user));

        authenticationService.confirmUser(code);

        assertTrue(user.isEnabled());
        verify(usersRepository).save(user);
    }

    @Test
    void confirmUser_shouldThrowWhenCodeNotFound() {
        when(usersRepository.findUserByCode("invalid")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authenticationService.confirmUser("invalid"));
    }
}