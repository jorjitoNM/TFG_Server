package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.domain.service.SocialService;
import org.server.ui.common.UiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(UiConstants.SOCIAL_URL)
@RequiredArgsConstructor
public class SocialController {

    private final SocialService service;

    @PostMapping(UiConstants.LIKE_URL)
    public ResponseEntity<Void> likeNote(@RequestParam Integer noteId,@RequestParam UUID userId) {
        if (service.likeNote(noteId, userId))
            return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
    }

    @PostMapping("/saveds")
    public ResponseEntity<Boolean> addNoteToSaved(
            @RequestParam String username,
            @RequestParam int noteId) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.addNoteToSaved(username, noteId));
    }
}
