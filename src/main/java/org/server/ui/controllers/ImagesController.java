package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.domain.service.ImagesService;
import org.server.domain.service.UserService;
import org.server.ui.common.UiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController(UiConstants.IMAGES_URL)
@RequiredArgsConstructor
@RequestMapping(UiConstants.IMAGES_URL)
public class ImagesController {

    private final ImagesService imagesService;
    private final UserService userService;

    @GetMapping(UiConstants.GET_FIREBASE_ID_URL)
    public ResponseEntity<String> getUserFirebaseId () {
        return ResponseEntity.ok(userService.getUserFirebaseId(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
