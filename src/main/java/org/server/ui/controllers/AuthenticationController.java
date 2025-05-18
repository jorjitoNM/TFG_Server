package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.AuthenticationService;
import org.server.security.jwt.Token;
import org.server.ui.model.AuthenticationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Token> login (@RequestBody AuthenticationUser authenticationUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationUser.getUsername(),
                        authenticationUser.getPassword()));
        return ResponseEntity.ok(service.generateToken(authenticationUser));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register (@RequestBody AuthenticationUser authenticationUser) {
        service.register(authenticationUser);
        return ResponseEntity.ok().build();
    }
}
