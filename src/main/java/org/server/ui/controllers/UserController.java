package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.UserService;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestParam String username) { return ResponseEntity.ok(userService.getUser(username)); }
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUserStartsWithText(@RequestParam String text) { return ResponseEntity.ok(userService.getAllUserStartsWithText(text)); }



    @GetMapping("/notes")
    public ResponseEntity<List<NoteDTO>> getNote(@RequestParam String username) {
        return ResponseEntity.ok(userService.getNoteByUsername(username));
    }
}
