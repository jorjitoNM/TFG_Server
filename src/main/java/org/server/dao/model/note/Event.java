package org.server.dao.model.note;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("EVENT")
@Getter
@Setter
public class Event extends Note {
    @Column(nullable = true)
    private LocalDateTime start;
    @Column(nullable = true)
    private LocalDateTime end;
}
