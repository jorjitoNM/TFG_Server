package org.server.dao.model.note;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("HISTORICAL")
public class NoteHistorical extends Note{
}
