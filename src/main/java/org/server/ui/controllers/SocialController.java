package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.domain.service.SocialService;
import org.server.ui.common.UiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UiConstants.SOCIAL_URL)
@RequiredArgsConstructor
public class SocialController {

    private final SocialService service;

    @PostMapping(UiConstants.LIKE_URL)
    public ResponseEntity<Void> likeNote(@RequestBody Integer noteId) {
        if (service.likeNote(noteId, SecurityContextHolder.getContext().getAuthentication().getName()))
            return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
    }
}
