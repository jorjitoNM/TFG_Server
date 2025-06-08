package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.domain.service.AuthenticationService;
import org.server.security.common.SecurityConstants;
import org.server.security.jwt.Token;
import org.server.ui.common.UiConstants;
import org.server.ui.model.AuthenticationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping(UiConstants.LOGIN_URL)
    public ResponseEntity<Token> login (@RequestBody AuthenticationUser authenticationUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationUser.getEmail(),
                        authenticationUser.getPassword()));
        return ResponseEntity.ok(service.generateToken(authenticationUser.getEmail()));
    }

    @PostMapping(UiConstants.REGISTER_URL)
    public ResponseEntity<Void> register (@RequestBody AuthenticationUser authenticationUser) {
        service.register(authenticationUser);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(null);
    }

    @GetMapping(UiConstants.REFRESH_URL)
    public ResponseEntity<Token> refresh (@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER)) {
            return ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).build();
        }
        final String token = authHeader.split(" ")[1].trim();
        if (service.isTokenValid(token))
            return ResponseEntity.ok(service.generateToken(
                    service.extractUsername(token)));
        else
            return ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).build();
    }

    @GetMapping(UiConstants.CONFIRM_URL)
    public ResponseEntity<String> confirm(@RequestParam("code") String code) {
        service.confirmUser(code);
        return ResponseEntity.status(HttpServletResponse.SC_ACCEPTED).body(Constantes.ACCEPTED);
    }
}
