package org.server.domain.service;

import lombok.AllArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.ui.model.NoteDTO;
import org.server.ui.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserSavedRepository userSavedRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public List<Note> getSavedNotesForUser(String username) {
        return userSavedRepository.findByUserUsername(username)
                .stream()
                .map(UserSavedNote::getNote)
                .toList();
    }

    public boolean addNoteToSaved(String username, int noteId) {
        boolean alreadyExists = userSavedRepository.findByUserUsername(username).stream()
                .anyMatch(savedNote -> savedNote.getNote().getId() == (noteId));

        if (alreadyExists) {
            return false;
        }

        UserSavedNote newSavedNote = new UserSavedNote();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        newSavedNote.setUser(user);
        newSavedNote.setNote(note);
        userSavedRepository.save(newSavedNote);
        return true;
    }

    public UserDTO getUser (String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }


    private UserDTO toDTO(User user) {
        List<UserDTO> followersDTO = user.getFollowers()
                .stream()
                .map(follower -> new UserDTO(
                        follower.getId(),
                        follower.getUsername(),
                        null, // No exponer password
                        follower.getEmail(),
                        follower.getRol(),
                        null, // Evitar ciclos infinitos
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
                        note.getType()
                )).toList();

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null, // No exponer password
                user.getEmail(),
                user.getRol(),
                followersDTO,
                followingDTO,
                notesDTO
        );
    }

}
