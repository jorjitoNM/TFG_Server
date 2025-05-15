package org.server.dao.model.note;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@DiscriminatorValue("HISTORICAL")
@Getter
@Setter
public class Historical extends Note{
}
