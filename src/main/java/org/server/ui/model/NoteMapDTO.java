package org.server.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NoteMapDTO {
    private double latitude;
    private double longitude;
    private int totalLikes;
    private List<NoteDTO> notes;
}
