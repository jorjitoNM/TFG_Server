package org.server.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SocialServiceTest {
    @InjectMocks
    private SocialService socialService;

    @Mock
    private UserLikesNotesRepository likesNotesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserSavedRepository userSavedRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void likeNote_WhenNoteNotLikedBefore_ShouldSaveLike() {
        User user = new User();
        user.setEmail("user@example.com");
        Note note = new Note();
        note.setId(1);

        UserLikedNote likedNote = new UserLikedNote(user, note);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(likesNotesRepository.findUserLikedNoteByUserAndNote(user, note)).thenReturn(Optional.empty());
        when(likesNotesRepository.save(any())).thenReturn(likedNote);

        boolean result = socialService.likeNote(1, "user@example.com");
        assertTrue(result);
    }

    @Test
    void likeNote_WhenNoteAlreadyLiked_ShouldNotSaveAgain() {
        User user = new User();
        user.setEmail("user@example.com");
        Note note = new Note();
        note.setId(1);
        UserLikedNote likedNote = new UserLikedNote(user, note);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(likesNotesRepository.findUserLikedNoteByUserAndNote(user, note)).thenReturn(Optional.of(likedNote));

        boolean result = socialService.likeNote(1, "user@example.com");
        assertTrue(result);
        verify(likesNotesRepository, never()).save(any());
    }

    @Test
    void addNoteToSaved_WhenNoteAlreadySaved_ReturnsFalse() {
        String email = "user@example.com";
        int noteId = 1;
        Note note = new Note();
        note.setId(noteId);
        UserSavedNote savedNote = new UserSavedNote();
        savedNote.setNote(note);

        when(userSavedRepository.findByUserEmail(email)).thenReturn(List.of(savedNote));

        boolean result = socialService.addNoteToSaved(email, noteId);
        assertFalse(result);
        verify(userRepository, never()).findByEmail(any());
        verify(noteRepository, never()).findById(anyInt());
    }

    @Test
    void addNoteToSaved_WhenNoteNotAlreadySaved_ReturnsTrue() {
        String email = "user@example.com";
        int noteId = 1;

        when(userSavedRepository.findByUserEmail(email)).thenReturn(List.of());

        User user = new User();
        Note note = new Note();
        note.setId(noteId);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        boolean result = socialService.addNoteToSaved(email, noteId);
        assertTrue(result);
        verify(userSavedRepository).save(any(UserSavedNote.class));
    }
}