package org.server.domain.model.note;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class Event extends Note {
    private LocalDateTime start;
    private LocalDateTime end;
}
