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
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserRepository;
import org.server.domain.errors.InvalidNoteTypeException;
import org.server.domain.errors.NoValidUserException;
import org.server.domain.errors.NoteNotFoundException;
import org.server.domain.errors.RatingOutOfBoundsException;
import org.server.ui.model.NoteDTO;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note note;
    private NoteDTO noteDTO;
    private User user;

    @BeforeEach
    void setup() {
        note = new Note();
        note.setId(1);
        note.setTitle("Test Note");
        note.setContent("Content");
        note.setType(NoteType.EVENT);
        note.setPrivacy(NotePrivacy.PRIVATE);
        note.setLatitude(1.0);
        note.setLongitude(2.0);
        note.setRating(3);
        note.setLikes(10);
        note.setCreated(LocalDateTime.now());

        user = new User();
        user.setUsername("paco");
        note.setOwner(user);

        noteDTO = new NoteDTO();
        noteDTO.setId(1);
        noteDTO.setTitle("Test Note");
        noteDTO.setContent("Content");
        noteDTO.setPrivacy(NotePrivacy.PRIVATE);
        noteDTO.setLatitude(1.0);
        noteDTO.setLongitude(2.0);
    }

    @Test
    void testGetNoteById_found() {
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        NoteDTO result = noteService.getNoteById(1);
        assertEquals(note.getTitle(), result.getTitle());
    }

    @Test
    void testGetNoteById_notFound() {
        when(noteRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoteNotFoundException.class, () -> noteService.getNoteById(1));
    }

    @Test
    void testAddNote_validUserAndValidNote() {
        when(userRepository.findByOwnUsername("paco")).thenReturn(user);
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note result = noteService.addNote(note, "paco");
        assertEquals(note.getTitle(), result.getTitle());
    }

    @Test
    void testAddNote_invalidUser() {
        when(userRepository.findByOwnUsername("unknown")).thenReturn(null);
        assertThrows(NoValidUserException.class, () -> noteService.addNote(note, "unknown"));
    }

    @Test
    void testAddNote_invalidNote() {
        note.setTitle("");
        when(userRepository.findByOwnUsername("paco")).thenReturn(user);
        assertThrows(InvalidNoteTypeException.class, () -> noteService.addNote(note, "paco"));
    }

    @Test
    void testUpdateNoteFromDTO_success() {
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);

        NoteDTO result = noteService.updateNoteFromDTO(noteDTO);
        assertEquals("Test Note", result.getTitle());
    }

    @Test
    void testRateNoteAndReturnDTO_success() {
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);

        NoteDTO result = noteService.rateNoteAndReturnDTO(1, 5);
        assertEquals(5, result.getRating());
    }

    @Test
    void testRateNote_invalidRating() {
        assertThrows(RatingOutOfBoundsException.class, () -> noteService.rateNoteAndReturnDTO(1, 6));
    }

    @Test
    void testFindNotesByType() {
        when(noteRepository.findByType(NoteType.CULTURAL)).thenReturn(List.of(note));
        note.setType(NoteType.CULTURAL);

        List<NoteDTO> result = noteService.findNotesByType(NoteType.CULTURAL);

        assertEquals(1, result.size());
        assertEquals(NoteType.CULTURAL, result.getFirst().getType());
    }

    @Test
    void testSortNoteList_descending() {
        when(noteRepository.findAll(any(Sort.class))).thenReturn(List.of(note));
        List<NoteDTO> result = noteService.sortNoteList(false);
        assertEquals(1, result.size());
    }
}