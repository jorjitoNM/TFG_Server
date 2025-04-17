package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.dao.repositories.mongodb.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagesService {

    private final ImageRepository imageRepository;

    public boolean saveNoteImages (int noteId,  MultipartFile image) {

        return false;
    }
}
