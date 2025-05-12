package org.server.domain.service;

import org.server.dao.model.note.Event;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserRepository;
import org.server.domain.errors.*;
import org.server.ui.model.EventNoteDTO;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.NoteMapDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<NoteDTO> findNotesByGeographicArea(double latitude, double longitude) {
        return noteRepository.findNotesByGeographicArea(latitude, longitude).stream().map(this::toDTO).toList();
    }


    public NoteDTO updateNoteFromDTO(NoteDTO noteDTO) {
        Note existingNote = noteRepository.findById(noteDTO.getId())
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteDTO.getId()));

        existingNote.setTitle(noteDTO.getTitle());
        existingNote.setContent(noteDTO.getContent());
        existingNote.setPrivacy(noteDTO.getPrivacy());
        existingNote.setLatitude(noteDTO.getLatitude());
        existingNote.setLongitude(noteDTO.getLongitude());

        Note savedNote = noteRepository.save(existingNote);
        return toDTO(savedNote);
    }

    public NoteDTO rateNoteAndReturnDTO(int noteId, int rating) {
        if (rating < 0 || rating > 5) {
            throw new RatingOutOfBoundsException("Rating must be between 0 and 5");
        }

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotAccessException("Note not found or you don't have permission to rate it"));

        note.setRating(rating);
        Note savedNote = noteRepository.save(note);
        return toDTO(savedNote);
    }

    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll().stream().map(this::toDTO).toList();
    }

    public NoteDTO getNoteById(int noteId) {
        Note n = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        return toDTO(n);
    }


    private NoteDTO toDTO(Note note) {
        if (note == null) {
            return null;
        }
        NoteDTO dto;

        if (note.getType() == NoteType.EVENT && note instanceof Event event) {
            EventNoteDTO eventDto = new EventNoteDTO();
            eventDto.setStart(event.getStart().toString());
            eventDto.setEnd(event.getEnd().toString());
            dto = eventDto;
        } else {
            dto = new NoteDTO();
        }

        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setPrivacy(note.getPrivacy());
        dto.setRating(note.getRating());
        dto.setOwnerUsername(note.getOwner() != null ? note.getOwner().getUsername() : null);
        dto.setLikes(note.getLikes());
        dto.setCreated(note.getCreated());
        dto.setLatitude(note.getLatitude());
        dto.setLongitude(note.getLongitude());
        dto.setType(note.getType());

        return dto;
    }

    public Note addNote(Note note, String username) {
        User user = userRepository.findByOwnUsername(username);
        if(user == null){
            throw new NoValidUserException("This user is not valid");
        }else{
            if(checkNote(note)){
                note.setOwner(user);
                note.setCreated(LocalDateTime.now());
                return noteRepository.save(note);
            }else{
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


    public List<NoteDTO> findNotesByType(NoteType type) {
        return noteRepository.findByType(type).stream().map(this::toDTO).toList();
    }
    public List<NoteDTO> sortNoteList(boolean ascending) {
        Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, "likes");
        List<Note> notes = noteRepository.findAll(sort);
        return notes.stream().map(this::toDTO).toList();
    }




}
