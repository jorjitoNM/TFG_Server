package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.domain.service.AuthenticationService;
import org.server.security.jwt.Token;
import org.server.ui.common.UiConstants;
import org.server.ui.model.AuthenticationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping(UiConstants.LOGIN_URL)
    public ResponseEntity<Token> login (@RequestBody AuthenticationUser authenticationUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationUser.getUsername(),
                        authenticationUser.getPassword()));
        return ResponseEntity.ok(service.generateToken(authenticationUser));
    }

    @PostMapping(UiConstants.REGISTER_URL)
    public ResponseEntity<Void> register (@RequestBody AuthenticationUser authenticationUser) {
        service.register(authenticationUser);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(null);
    }
}
