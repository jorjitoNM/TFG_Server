package org.server.dao.repositories.mongodb;

import org.bson.types.ObjectId;
import org.server.dao.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<Image, ObjectId> {

}
