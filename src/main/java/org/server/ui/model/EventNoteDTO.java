package org.server.ui.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class EventNoteDTO extends NoteDTO {
   private String start;
   private String end;
}