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
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserSavedRepository userSavedRepository;
    private final UserLikesNotesRepository userLikesNotesRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final Mapper mapper;

    public List<UserDTO> getAllUserStartsWithText(String text) {

        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().toLowerCase().startsWith(text.toLowerCase()))
                .map(this::toDTO)
                .toList();
    }

    public List<NoteDTO> getLikedNotes(String username) {
        return userLikesNotesRepository.findAll().stream()
                .map(UserLikedNote::getNote)
                .map(it -> mapper.toDTO(it, username))
                .toList();
    }

    public List<NoteDTO> getSavedNotesForUser(String username) {
        return userSavedRepository.findByUserUsername(username)
                .stream()
                .map(UserSavedNote::getNote)
                .map(note -> mapper.toDTO(note, username))
                .toList();
    }


    @Transactional
    public void removeSavedNotesForUser(String username, int noteId) {
        int deletedCount = userSavedRepository.deleteByNoteAndUser(noteId, username);

        if (deletedCount == 0) {
            throw new NoteNotFoundException(
                    "Note with id " + noteId + " not found in saved notes for user " + username);
        }
    }

    @Transactional
    public void removeLikedNotesForUser(String username, int noteId) {
        int deletedCount = userLikesNotesRepository.deleteByNoteAndUser(noteId, username);

        if (deletedCount == 0) {
            throw new NoteNotFoundException(
                    "Note with id " + noteId + " not found in liked notes for user " + username);
        }
    }


    public UserDTO getUser (String username) {
        User user = userRepository.findByOwnUsername(username);
        return toDTO(user);
    }

    public List<NoteDTO> getNoteByUsername(String username) {
        return noteRepository.findByOwnerUsername(username).stream()
                .map(note -> mapper.toDTO(note, username))
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
                null,
                user.getEmail(),

                notesDTO
        );
    }
}
