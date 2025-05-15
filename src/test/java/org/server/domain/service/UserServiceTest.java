package org.server.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NotePrivacy;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.UserSavedRepository;
import org.server.ui.model.NoteDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserSavedRepository userSavedRepository;

    @InjectMocks
    private UserService userService;

    private Note note;
    private User user;
    private UserSavedNote userSavedNote;


    @BeforeEach
    void setUp() {
        note = new Note();
        note.setId(1);
        note.setTitle("Test Note");
        note.setContent("Content");
        note.setPrivacy(NotePrivacy.PRIVATE);
        note.setRating(4);
        note.setLikes(10);
        note.setLatitude(1.0);
        note.setLongitude(2.0);
        note.setCreated(LocalDateTime.now());
        note.setType(NoteType.CULTURAL);

        user = new User();
        user.setUsername("pepe");
        note.setOwner(user);

        userSavedNote = new UserSavedNote();
        userSavedNote.setNote(note);
        userSavedNote.setUser(user);
    }

    @Test
    void testGetSavedNotesForUser_returnsList() {
        when(userSavedRepository.findByUserUsername("pepe")).thenReturn(List.of(userSavedNote));

        List<NoteDTO> result = userService.getSavedNotesForUser("pepe");

        assertEquals(1, result.size());
        assertEquals("Test Note", result.getFirst().getTitle());
        assertEquals("pepe", result.getFirst().getOwnerUsername());
        assertEquals(NoteType.CULTURAL, result.getFirst().getType());
    }

    @Test
    void testGetSavedNotesForUser_returnsEmptyList() {
        when(userSavedRepository.findByUserUsername("unknown_user")).thenReturn(List.of());

        List<NoteDTO> result = userService.getSavedNotesForUser("unknown_user");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetSavedNotesForUser_withNullNote() {
        UserSavedNote invalidSavedNote = new UserSavedNote();
        invalidSavedNote.setNote(null);

        when(userSavedRepository.findByUserUsername("pepe")).thenReturn(List.of(invalidSavedNote));

        List<NoteDTO> result = userService.getSavedNotesForUser("pepe");

        assertEquals(1, result.size());
        assertNull(result.getFirst());
    }

}