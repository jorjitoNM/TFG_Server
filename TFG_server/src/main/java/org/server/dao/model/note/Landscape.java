package org.server.dao.model.note;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LANDSCAPE")
public class Landscape extends Note{
}
