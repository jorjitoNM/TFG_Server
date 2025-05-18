package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.user.User;
import org.server.dao.repositories.UsersRepository;
import org.server.security.jwt.JWTService;
import org.server.security.jwt.Token;
import org.server.ui.model.AuthenticationUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private UsersRepository usersRepository;

    public Token generateToken(AuthenticationUser authenticationUser) {
        return jwtService.generateToken(
                Map.of(Constantes.USERNAME, authenticationUser.getUsername()),
                userDetailsService.loadUserByUsername(authenticationUser.getUsername()));
    }

    public void register(AuthenticationUser authenticationUser) {
        byte[] randomCode = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(randomCode);
        String code = Base64.getUrlEncoder().encodeToString(randomCode);
        usersRepository.save(new User(
                authenticationUser.getUsername(),
                authenticationUser.getEmail(),
                authenticationUser.getPassword(),
                code,
                false)
        );
    }
}
