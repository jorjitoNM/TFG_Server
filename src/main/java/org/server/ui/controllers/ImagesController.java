package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.domain.service.ImagesService;
import org.server.ui.common.UiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController(UiConstants.IMAGES_URL)
@RequiredArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;

    public ResponseEntity<Boolean> saveNoteImages (@RequestParam int noteId, @RequestParam List<MultipartFile> images) {
        if (imagesService.saveNoteImages(noteId,images))
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.status(HttpServletResponse.SC_NOT_MODIFIED).body(false);
    }


}
