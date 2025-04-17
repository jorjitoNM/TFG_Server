package org.server.ui.model;

import lombok.Data;
import org.server.dao.model.note.NoteType;

@Data
public class NoteMapDTO {
    private int id;
    private double latitude;
    private double longitude;
    private NoteType type;
}
