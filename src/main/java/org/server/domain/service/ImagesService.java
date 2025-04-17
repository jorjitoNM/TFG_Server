package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.server.dao.model.Image;
import org.server.dao.repositories.mongodb.ImageRepository;
import org.server.domain.common.DomainConstants;
import org.server.domain.errors.images.CouldNotReadImageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagesService {

    private final ImageRepository imageRepository;

    public boolean saveNoteImages (int noteId,  List<MultipartFile> images) {
        List<Image> modelImages = new ArrayList<>();
        images.forEach(i -> {
            try {
                modelImages.add(new Image(noteId,new Binary(i.getBytes()),i.getContentType()));
            } catch (IOException e) {
                throw new CouldNotReadImageException(400, DomainConstants.COULD_NOT_READ_IMAGE);
            }
        });
        return imageRepository.saveAll(modelImages).size() == images.size();
    }

    public List<Image> getNoteImages (int noteId) {
        return imageRepository.findAllByNoteId(noteId);
    }
}
