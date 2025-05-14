package org.server.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.domain.errors.NoteNotFoundException;
import org.server.domain.errors.UserNotFound;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocialServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserLikesNotesRepository likesNotesRepository;

    @Mock
    private UserSavedRepository userSavedRepository;

    @InjectMocks
    private SocialService socialService;


    @Test
    void likeNote_UserAndNoteExist_ReturnsTrue() {
        UUID userId = UUID.randomUUID();
        Integer noteId = 1;
        User mockUser = new User();
        mockUser.setId(userId);
        Note mockNote = new Note();
        mockNote.setId(noteId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));
        when(likesNotesRepository.findUserLikedNoteByUserAndNote(mockUser, mockNote))
                .thenReturn(Optional.empty());
        when(likesNotesRepository.save(any(UserLikedNote.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = socialService.likeNote(noteId, userId);

        assertTrue(result);
        verify(likesNotesRepository).save(any(UserLikedNote.class));
    }

    @Test
    void likeNote_UserNotFound_ThrowsException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> socialService.likeNote(1, userId));
    }

    @Test
    void likeNote_NoteNotFound_ThrowsException() {
        UUID userId = UUID.randomUUID();
        User mockUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> socialService.likeNote(1, userId));
    }

    @Test
    void addNoteToSaved_NewNote_ReturnsTrue() {

        String username = "testUser";
        int noteId = 1;
        User mockUser = new User();
        Note mockNote = new Note();
        mockNote.setId(noteId);

        when(userSavedRepository.findByUserUsername(username)).thenReturn(List.of());
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));
        when(userSavedRepository.save(any(UserSavedNote.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = socialService.addNoteToSaved(username, noteId);

        assertTrue(result);
        verify(userSavedRepository).save(any(UserSavedNote.class));
    }

    @Test
    void addNoteToSaved_NoteAlreadySaved_ReturnsFalse() {
        String username = "testUser";
        int noteId = 1;
        UserSavedNote existingSavedNote = new UserSavedNote();
        existingSavedNote.setNote(new Note());
        existingSavedNote.getNote().setId(noteId);

        when(userSavedRepository.findByUserUsername(username))
                .thenReturn(List.of(existingSavedNote));

        boolean result = socialService.addNoteToSaved(username, noteId);

        assertFalse(result);
        verify(userSavedRepository, never()).save(any());
    }

    @Test
    void addNoteToSaved_UserNotFound_ThrowsException() {
        String username = "unknownUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> socialService.addNoteToSaved(username, 1));
    }

    @Test
    void addNoteToSaved_NoteNotFound_ThrowsException() {
        String username = "testUser";
        User mockUser = new User();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> socialService.addNoteToSaved(username, 1));
    }
}