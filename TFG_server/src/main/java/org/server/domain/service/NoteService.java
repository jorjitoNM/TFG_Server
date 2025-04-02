package org.server.domain.service;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.repositories.UserRepository;
import org.server.domain.errors.*;
import org.server.dao.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.server.dao.model.note.Event;
import org.server.dao.model.note.Note;

import org.server.ui.model.EventNoteDTO;
import org.server.ui.model.NoteDTO;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }


    public List<NoteDTO> findNotesByGeographicArea(double latitude, double longitude, double radiusKm) {
        //lo saque de v0
        // Convert radius from km to degrees (approximate)
        // 1 degree of latitude = ~111 km
        // 1 degree of longitude = ~111 km * cos(latitude)
        double latDelta = radiusKm / 111.0;
        double lngDelta = radiusKm / (111.0 * Math.cos(Math.toRadians(latitude)));

        double minLat = latitude - latDelta;
        double maxLat = latitude + latDelta;
        double minLng = longitude - lngDelta;
        double maxLng = longitude + lngDelta;

        List<Note> notes = noteRepository.findNotesByGeographicArea(minLat, maxLat, minLng, maxLng);
        return toDTOList(notes);
    }


    public Note updateNote(int noteId, Note updatedNote, String username) {
        Note existingNote = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));


        if (!existingNote.getOwner().getUsername().equals(username)) {
            throw new NoteNotBelongUserException("You don't have permission to edit this note");
        }
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        existingNote.setPrivacy(updatedNote.getPrivacy());
        existingNote.setLatitude(updatedNote.getLatitude());
        existingNote.setLongitude(updatedNote.getLongitude());


        return noteRepository.save(existingNote);
    }

    public Note rateNote(int noteId, int rating, String username) {

        if (rating < 0 || rating > 10) {
            throw new RatingOutOfBoundsException("Rating must be between 0 and 10");
        }


        Note note = noteRepository.findByIdAndOwnerUsername(noteId, username)
                .orElseThrow(() -> new NoteNotAccessException("Note not found or you don't have permission to rate it"));


        note.setRating(rating);


        return noteRepository.save(note);
    }


    private NoteDTO toDTO(Note note) {
        if (note == null) {
            return null;
        }

        NoteDTO dto;
        if (note instanceof Event event) {
            EventNoteDTO eventDto = new EventNoteDTO();
            eventDto.setStart(event.getStart());
            eventDto.setEnd(event.getEnd());
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
    private List<NoteDTO> toDTOList(List<Note> notes) {
        return notes.stream()
                .map(this::toDTO)
                .toList();
    }

    public Note addNote(Note note, String username) {
        User user = userRepository.findByUsername(username);
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

    public List<Note> findNotesByType(NoteType type) {
        return noteRepository.findByType(type);
    }
}
