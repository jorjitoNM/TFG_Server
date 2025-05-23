package org.server.common;

import lombok.RequiredArgsConstructor;
import org.server.dao.model.note.Event;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.ui.model.EventNoteDTO;
import org.server.ui.model.NoteDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final UserSavedRepository userSavedRepository;
    private final UserLikesNotesRepository userLikesNotesRepository;

    public NoteDTO toDTO(Note note, String username) {
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
        dto.setSaved(userSavedRepository.existsByUserUsernameAndNoteId(username, note.getId()));
        dto.setLiked(userLikesNotesRepository.existsByUserUsernameAndNoteId(username, note.getId()));
        return dto;
    }
}
