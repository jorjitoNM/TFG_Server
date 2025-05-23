package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.UserService;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUser(username)); }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteDTO>> getNote(@RequestParam String username) {
        return ResponseEntity.ok(userService.getNoteByUsername(username));
    }

    @GetMapping("/likes")
    public ResponseEntity<List<NoteDTO>> likeNotes() {
        return ResponseEntity.ok(userService.getLikedNotes("user1"));
    }
}
