package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.user.User;
import org.server.dao.repositories.UsersRepository;
import org.server.domain.common.DomainConstants;
import org.server.domain.errors.DuplicatedUsernameOrPasswordException;
import org.server.domain.errors.UserNotFoundException;
import org.server.security.jwt.JWTService;
import org.server.security.jwt.Token;
import org.server.ui.model.AuthenticationUser;
import org.server.utils.MailComponent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailComponent mailComponent;

    public Token generateToken(String email) {
        return jwtService.generateToken(
                Map.of(Constantes.EMAIL, email),
                userDetailsService.loadUserByUsername(email));
    }
    @Async
    public void register(AuthenticationUser authenticationUser) {
        byte[] randomCode = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(randomCode);
        String code = Base64.getUrlEncoder().encodeToString(randomCode);
        try {
            usersRepository.save(new User(
                    authenticationUser.getUsername(),
                    passwordEncoder.encode(authenticationUser.getPassword()),
                    authenticationUser.getEmail(),
                    code)
            );
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedUsernameOrPasswordException(DomainConstants.DUPLICATED_USERNAME_OR_PASSWORD);
        }
        mailComponent.sendVerificationEmail(authenticationUser.getEmail(),authenticationUser.getUsername(),
                "https://informatica.iesquevedo.es/nomada/confirm?code=" + code);
    }

    public boolean isTokenValid(String token) {
        return jwtService.isTokenValid(token,
                userDetailsService.loadUserByUsername(jwtService.extractUsername(token)));
    }

    public String extractUsername (String token) {
        return jwtService.extractUsername(token);
    }


    public void confirmUser(String code) {
        User u = usersRepository.findUserByCode(code).orElseThrow(UserNotFoundException::new);
        u.setEnabled(true);
        usersRepository.save(u);
    }
}
