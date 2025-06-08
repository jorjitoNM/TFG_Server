package org.server.domain.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.server.common.Mapper;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.domain.errors.NoteNotFoundException;
import org.server.domain.errors.UserNotFoundException;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserSavedRepository userSavedRepository;
    private final UserLikesNotesRepository userLikesNotesRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final Mapper mapper;

    public List<UserDTO> getAllUserStartsWithTextExceptCurrent(String text, String username) {

        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().toLowerCase().startsWith(text.toLowerCase()))
                .filter(user -> !user.getUsername().equals(username))
                .map(this::toDTO)
                .toList();
    }


    public List<NoteDTO> getLikedNotes(String email) {
        Optional<List<UserLikedNote>> likedNotes = userLikesNotesRepository.findALlByUserEmail(email);
        return likedNotes.map(userLikedNotes -> userLikedNotes.stream()
                .map(UserLikedNote::getNote)
                .map(note -> mapper.toDTO(note, email))
    }

    public List<NoteDTO> getSavedNotesForUser(String email) {
        return userSavedRepository.findByUserEmail(email)
                .stream()
                .map(UserSavedNote::getNote)
                .map(note -> mapper.toDTO(note, email))
                .toList();
    }


    @Transactional
    public void removeSavedNotesForUser(String email, int noteId) {
        int deletedCount = userSavedRepository.deleteByNoteAndUser(noteId, email);

        if (deletedCount == 0) {
            throw new NoteNotFoundException(
                    "Note with id " + noteId + " not found in saved notes for user " + email);
        }
    }

    @Transactional
    public void removeLikedNotesForUser(String email, int noteId) {
        int deletedCount = userLikesNotesRepository.deleteByNoteAndUser(noteId, email);

        if (deletedCount == 0) {
            throw new NoteNotFoundException(
                    "Note with id " + noteId + " not found in liked notes for user " + email);
        }
    }


    public UserDTO getUser (String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return toDTO(user);
    }

    public List<NoteDTO> getNoteByEmail(String email) {
        return noteRepository.findByOwnerEmail(email).stream()
                .map(note -> mapper.toDTO(note, email))
                .toList();
    }

    private UserDTO toDTO(User user) {

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
                user.getEmail(),
                notesDTO
        );
    }

    public String getUserFirebaseId(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new).getFirebaseId().toString();
    }
}
