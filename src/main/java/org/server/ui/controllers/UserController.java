package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.UserService;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.ok(userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUserStartsWithText(@RequestParam String text) {
        return ResponseEntity.ok(userService.getAllUserStartsWithText(text));
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<UserDTO> getUserInfo(String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteDTO>> getNote() {
        return ResponseEntity.ok(userService.getNotesByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping("/notes/{username}")
    public ResponseEntity<List<NoteDTO>> getNotesByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getNotesByUsername(username));
    }

    @GetMapping("/likes")
    public ResponseEntity<List<NoteDTO>> likeNotes() {
        return ResponseEntity.ok(userService.getLikedNotes(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
