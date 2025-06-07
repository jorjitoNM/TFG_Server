package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.UserService;
import org.server.ui.common.UiConstants;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<UserDTO>> getAllUserStartsWithTextExceptCurrent(@RequestParam String text) {
        return ResponseEntity.ok(userService.getAllUserStartsWithTextExceptCurrent(text, SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<UserDTO> getUserInfo(String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteDTO>> getNote() {
        return ResponseEntity.ok(userService.getNoteByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping("/notes/{username}")
    public ResponseEntity<List<NoteDTO>> getNotesByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getNoteByEmail(username));
    }

    @GetMapping("/likes")
    public ResponseEntity<List<NoteDTO>> likeNotes() {
        return ResponseEntity.ok(userService.getLikedNotes(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping(UiConstants.GET_FIREBASE_ID_URL)
    public ResponseEntity<Map<String, String>> getUserFirebaseId() {
        String firebaseId = userService.getUserFirebaseId(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        return ResponseEntity.ok(Collections.singletonMap("firebaseId", firebaseId));
    }
}
