package org.server.dao.model.note;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@DiscriminatorValue("CULTURAL")
@Getter
@Setter
public class Cultural extends Note {
}
