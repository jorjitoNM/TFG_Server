package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.common.Mapper;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserRepository;
import org.server.domain.errors.*;
import org.server.ui.model.NoteDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;


    public List<NoteDTO> findNotesByGeographicArea(double latitude, double longitude) {
        return noteRepository.findNotesByGeographicArea(latitude, longitude).stream().map(note -> mapper.toDTO(note,"alice")).toList();
    }


    public NoteDTO updateNoteFromDTO(NoteDTO noteDTO, String username) {
        Note existingNote = noteRepository.findById(noteDTO.getId())
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteDTO.getId()));

        existingNote.setTitle(noteDTO.getTitle());
        existingNote.setContent(noteDTO.getContent());
        existingNote.setPrivacy(noteDTO.getPrivacy());
        existingNote.setLatitude(noteDTO.getLatitude());
        existingNote.setLongitude(noteDTO.getLongitude());

        Note savedNote = noteRepository.save(existingNote);
        return mapper.toDTO(savedNote,username);
    }

    public NoteDTO rateNoteAndReturnDTO(int noteId, int rating, String username) {
        if (rating < 0 || rating > 5) {
            throw new RatingOutOfBoundsException("Rating must be between 0 and 5");
        }

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotAccessException("Note not found or you don't have permission to rate it"));

        note.setRating(rating);
        Note savedNote = noteRepository.save(note);
        return mapper.toDTO(savedNote,username);
    }

    public List<NoteDTO> getAllNotes(String username) {
        return noteRepository.findAll().stream().map(note -> mapper.toDTO(note,username)).toList();
    }

    public NoteDTO getNoteById(int noteId, String username) {
        Note n = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        return mapper.toDTO(n,username);
    }


    public Note addNote(Note note, String username) {
        User user = userRepository.findByOwnUsername(username);
        if (user == null) {
            throw new NoValidUserException("This user is not valid");
        } else {
            if (checkNote(note)) {
                note.setOwner(user);
                note.setCreated(LocalDateTime.now());
                return noteRepository.save(note);
            } else {
                throw new InvalidNoteTypeException("Invalid note type");
            }

        }
    }

    public boolean checkNote(Note note) {
        if (note == null || note.getType() == null || note.getTitle().isEmpty()) {
            return false;
        }
        try {
            NoteType.valueOf(String.valueOf(note.getType()));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    public List<NoteDTO> findNotesByType(NoteType type, String username) {
        return noteRepository.findByType(type).stream().map(note -> mapper.toDTO(note,username)).toList();
    }

    public List<NoteDTO> sortNoteList(boolean ascending) {
        Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, "likes");
        List<Note> notes = noteRepository.findAll(sort);
        return notes.stream().map(note -> mapper.toDTO(note,"user1")).toList();
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public Note getNoteByIdNote(int id) {
        return noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
    }
}
