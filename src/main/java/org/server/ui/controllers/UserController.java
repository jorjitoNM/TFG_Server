package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.UserService;
import org.server.ui.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestParam String username) { return ResponseEntity.ok(userService.getUser(username)); }

}
