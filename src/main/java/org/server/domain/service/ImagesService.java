package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.dao.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagesService {

    public boolean saveNoteImages (int noteId,  List<MultipartFile> images) {
        return false;
    }

    public List<Image> getNoteImages (int noteId) {
        return List.of();
    }
}
