package org.server.dao.repositories.mongodb;

import org.bson.types.ObjectId;
import org.server.dao.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends MongoRepository<Image, ObjectId> {
    List<Image> findAllByNoteId (int noteId);
}
