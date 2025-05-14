package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.server.dao.model.user.User;
import org.server.domain.service.SocialService;
import org.server.ui.common.UiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
