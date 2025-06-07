package org.server.common;

import lombok.RequiredArgsConstructor;
import org.server.dao.model.note.Event;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.ui.model.EventNoteDTO;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final UserSavedRepository userSavedRepository;
    private final UserLikesNotesRepository userLikesNotesRepository;

    public NoteDTO toDTO(Note note, String email) {
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
        dto.setSaved(userSavedRepository.existsByUserEmailAndNoteId(email, note.getId()));
        dto.setLiked(userLikesNotesRepository.existsByUserEmailAndNoteId(email, note.getId()));
        return dto;
    }

    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        List<NoteDTO> noteDTOs = user.getNotes() != null
                ? user.getNotes().stream().map(note -> toDTO(note, user.getUsername())).toList()
                : List.of();

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null,
                user.getEmail(),
                noteDTOs
        );
    }

}
