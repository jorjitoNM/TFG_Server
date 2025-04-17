package org.server.dao.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    private ObjectId imageId;
    private int noteId;
    private Binary image;
    private String imageType;

    public Image(int noteId, Binary image, String imageType) {
        this.noteId = noteId;
        this.image = image;
        this.imageType = imageType;
    }
}
