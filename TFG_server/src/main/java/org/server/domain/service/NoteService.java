package org.server.domain.service;
import org.server.dao.model.note.Event;
import org.server.dao.model.note.NoteType;
import org.server.domain.errors.NoteNotAccessException;
import org.server.domain.errors.NoteNotBelongUserException;
import org.server.domain.errors.NoteNotFoundException;
import org.server.domain.errors.RatingOutOfBoundsException;
import org.server.dao.repositories.NoteRepository;
import org.server.ui.model.EventNoteDTO;
import org.server.ui.model.NoteDTO;
import org.springframework.stereotype.Service;
import org.server.dao.model.note.Note;



import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> findNotesByGeographicArea(double latitude, double longitude) {
        return noteRepository.findNotesByGeographicArea(latitude,longitude).stream().map(this::toDTO).toList();
    }


    public NoteDTO updateNoteFromDTO(NoteDTO noteDTO) {
        Note existingNote = noteRepository.findById(noteDTO.getId())
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteDTO.getId()));

        // Update the entity from the DTO
        existingNote.setTitle(noteDTO.getTitle());
        existingNote.setContent(noteDTO.getContent());
        existingNote.setPrivacy(noteDTO.getPrivacy());
        existingNote.setLatitude(noteDTO.getLatitude());
        existingNote.setLongitude(noteDTO.getLongitude());

        // Save and convert back to DTO
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

    public List<NoteDTO> getAllNotes(){
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
            eventDto.setStart(event.getStart().toString()); // Convertir a String
            eventDto.setEnd(event.getEnd().toString());     // Convertir a String
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
        dto.setCreated(note.getCreated().toString());
        dto.setLatitude(note.getLatitude());
        dto.setLongitude(note.getLongitude());
        dto.setType(note.getType());

        return dto;
    }
    private List<NoteDTO> toDTOList(List<Note> notes) {
        return notes.stream()
                .map(this::toDTO)
                .toList();
    }




}
