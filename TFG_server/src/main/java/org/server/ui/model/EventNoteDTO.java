package org.server.ui.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = true)
public class EventNoteDTO extends NoteDTO {
   private LocalDateTime start;
   private LocalDateTime end;
}