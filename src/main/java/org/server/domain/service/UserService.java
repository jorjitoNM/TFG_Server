package org.server.domain.service;

import lombok.AllArgsConstructor;
import org.server.dao.model.note.Event;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserRepository;
import org.server.domain.errors.UserNotFoundException;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.server.ui.model.EventNoteDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserSavedRepository userSavedRepository;
    private final UserLikesNotesRepository userLikesNotesRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public List<UserDTO> getAllUserStartsWithText(String text) {

        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().toLowerCase().startsWith(text.toLowerCase()))
                .map(this::toDTO)
                .toList();
    }

    public List<NoteDTO> getSavedNotesForUser(String username) {
        return userSavedRepository.findByUserUsername(username)
                .stream()
                .map(UserSavedNote::getNote)
                .map(note -> toDTO(note, username))
                .toList();
    }

    private NoteDTO toDTO(Note note, String username) {
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

    public UserDTO getUser (String username) {
        User user = userRepository.findByOwnUsername(username);
        return toDTO(user);
    }
    public List<NoteDTO> getNoteByUsername(String username) {
        return noteRepository.findByOwnerUsername(username).stream()
                .map(note -> toDTO(note, username))
                .toList();
    }

    private UserDTO toDTO(User user) {
        List<UserDTO> followersDTO = user.getFollowers()
                .stream()
                .map(follower -> new UserDTO(
                        follower.getId(),
                        follower.getUsername(),
                        null,
                        follower.getEmail(),
                        follower.getRol(),
                        null,
                        null,
                        null
                )).toList();

        List<UserDTO> followingDTO = user.getFollowing()
                .stream()
                .map(following -> new UserDTO(
                        following.getId(),
                        following.getUsername(),
                        null,
                        following.getEmail(),
                        following.getRol(),
                        null,
                        null,
                        null
                )).toList();

        List<NoteDTO> notesDTO = user.getNotes()
                .stream()
                .map(note -> new NoteDTO(
                        note.getId(),
                        note.getTitle(),
                        note.getContent(),
                        note.getPrivacy(),
                        note.getRating(),
                        user.getUsername(),
                        note.getLikes(),
                        note.getCreated(),
                        note.getLatitude(),
                        note.getLongitude(),
                        note.getType(),false,false
                )).toList();

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null,
                user.getEmail(),
                user.getRol(),
                followersDTO,
                followingDTO,
                notesDTO
        );
    }


}
