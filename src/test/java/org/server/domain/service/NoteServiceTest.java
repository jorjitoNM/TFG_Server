package org.server.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.server.common.Mapper;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.domain.errors.RatingOutOfBoundsException;
import org.server.ui.model.EventNoteDTO;
import org.server.ui.model.NoteDTO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Mapper mapper;
    @Mock
    private UserSavedRepository userSavedNoteRepository;
    @Mock
    private UserLikesNotesRepository userLikedNoteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindNotesByGeographicArea() {
        Note note = new Note();
        when(noteRepository.findNotesByGeographicArea(1.0, 2.0)).thenReturn(List.of(note));
        when(mapper.toDTO(any(Note.class), eq("alice"))).thenReturn(new NoteDTO());
        assertEquals(1, noteService.findNotesByGeographicArea(1.0, 2.0).size());
    }

    @Test
    void testUpdateNoteFromDTO() {
        NoteDTO dto = new NoteDTO();
        dto.setId(1);
        dto.setTitle("Title");
        Note note = new Note();
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);
        when(mapper.toDTO(note, "user")).thenReturn(dto);
        assertEquals(dto, noteService.updateNoteFromDTO(dto, "user"));
    }

    @Test
    void testRateNoteAndReturnDTO_valid() {
        Note note = new Note();
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);
        when(mapper.toDTO(note, "user")).thenReturn(new NoteDTO());
        assertNotNull(noteService.rateNoteAndReturnDTO(1, 5, "user"));
    }

    @Test
    void testRateNoteAndReturnDTO_invalidRating() {
        assertThrows(RatingOutOfBoundsException.class, () -> noteService.rateNoteAndReturnDTO(1, 11, "user"));
    }

    @Test
    void testGetAllNotes() {
        when(noteRepository.findAll()).thenReturn(List.of(new Note()));
        when(mapper.toDTO(any(Note.class), eq("user"))).thenReturn(new NoteDTO());
        assertEquals(1, noteService.getAllNotes("user").size());
    }

    @Test
    void testGetNoteById() {
        Note note = new Note();
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(mapper.toDTO(note, "user")).thenReturn(new NoteDTO());
        assertNotNull(noteService.getNoteById(1, "user"));
    }

    @Test
    void testAddNoteFromDTO_Event() {
        EventNoteDTO dto = new EventNoteDTO();
        dto.setType(NoteType.EVENT);
        dto.setStart("2024-01-01T00:00:00");
        dto.setEnd("2024-01-02T00:00:00");
        dto.setTitle("Title");
        User user = new User();
        when(userRepository.findByEmail("user")).thenReturn(Optional.of(user));
        when(noteRepository.save(any(Note.class))).thenAnswer(i -> i.getArgument(0));
        when(mapper.toDTO(any(Note.class), eq("user"))).thenReturn(new NoteDTO());
        assertNotNull(noteService.addNoteFromDTO(dto, "user"));
    }

    @Test
    void testCheckNote_valid() {
        Note note = new Note();
        note.setTitle("Title");
        note.setType(NoteType.CULTURAL);
        assertTrue(noteService.checkNote(note));
    }

    @Test
    void testCheckNote_invalid() {
        Note note = new Note();
        note.setTitle("");
        assertFalse(noteService.checkNote(note));
    }

    @Test
    void testFindNotesByType() {
        Note note = new Note();
        when(noteRepository.findByType(NoteType.CULTURAL)).thenReturn(List.of(note));
        when(mapper.toDTO(note, "user")).thenReturn(new NoteDTO());
        assertEquals(1, noteService.findNotesByType(NoteType.CULTURAL, "user").size());
    }

    @Test
    void testSortNoteList() {
        Note note = new Note();
        when(noteRepository.findAll(any(Sort.class))).thenReturn(List.of(note));
        when(mapper.toDTO(note, "user")).thenReturn(new NoteDTO());
        assertEquals(1, noteService.sortNoteList(true, "user").size());
    }

    @Test
    void testDeleteNote() {
        Note note = new Note();
        note.setId(1);
        doNothing().when(userSavedNoteRepository).deleteAllByNoteId(1);
        doNothing().when(userLikedNoteRepository).deleteAllByNoteId(1);
        doNothing().when(noteRepository).delete(note);
        assertDoesNotThrow(() -> noteService.deleteNote(note));
    }

    @Test
    void testGetNoteByIdNote() {
        Note note = new Note();
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        assertEquals(note, noteService.getNoteByIdNote(1));
    }

    @Test
    void testSortNoteListByAntiquity() {
        Note note = new Note();
        when(noteRepository.findAll(any(Sort.class))).thenReturn(List.of(note));
        when(mapper.toDTO(note, "user")).thenReturn(new NoteDTO());
        assertEquals(1, noteService.sortNoteListByAntiquity(true, "user").size());
    }
}