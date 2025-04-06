package org.server.domain.service;

import lombok.AllArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.dao.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> sortNoteList(boolean ascending) {
        return ascending ? noteRepository.findAllByOrderByLikesAsc() : noteRepository.findAllByOrderByLikesDesc();
    }
}
