package org.server.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.server.common.Mapper;
import org.server.dao.model.note.Event;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NotePrivacy;
import org.server.dao.model.note.NoteType;
import org.server.dao.model.user.User;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.ui.model.EventNoteDTO;
import org.server.ui.model.NoteDTO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UserServiceTest {
    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSavedRepository userSavedRepository;

    @Mock
    private UserLikesNotesRepository userLikesNotesRepository;

    @Mock
    private Mapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findNotesByGeographicArea_ReturnsNoteDTOList() {
        Note note = new Note();
        NoteDTO dto = new NoteDTO();
        when(noteRepository.findNotesByGeographicArea(anyDouble(), anyDouble())).thenReturn(List.of(note));
        when(mapper.toDTO(note, "alice")).thenReturn(dto);

        List<NoteDTO> result = noteService.findNotesByGeographicArea(1.0, 2.0);
        assertEquals(1, result.size());
    }

    @Test
    void updateNoteFromDTO_ShouldUpdateAndReturnDTO() {
        Note existingNote = new Note();
        existingNote.setId(1);
        NoteDTO inputDTO = new NoteDTO();
        inputDTO.setId(1);
        inputDTO.setTitle("New Title");
        inputDTO.setContent("Content");
        inputDTO.setPrivacy(NotePrivacy.PRIVATE);
        inputDTO.setLatitude(0.0);
        inputDTO.setLongitude(0.0);

        Note savedNote = new Note();
        savedNote.setId(1);
        NoteDTO expectedDTO = new NoteDTO();
        expectedDTO.setId(1);

        when(noteRepository.findById(1)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);
        when(mapper.toDTO(savedNote, "alice")).thenReturn(expectedDTO);

        NoteDTO result = noteService.updateNoteFromDTO(inputDTO, "alice");
        assertEquals(expectedDTO.getId(), result.getId());
    }

    @Test
    void rateNoteAndReturnDTO_ShouldWork() {
        Note note = new Note();
        note.setId(1);
        Note savedNote = new Note();
        NoteDTO dto = new NoteDTO();
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(savedNote);
        when(mapper.toDTO(savedNote, "alice")).thenReturn(dto);

        NoteDTO result = noteService.rateNoteAndReturnDTO(1, 5, "alice");
        assertNotNull(result);
    }

    @Test
    void getAllNotes_ReturnsList() {
        Note note = new Note();
        NoteDTO dto = new NoteDTO();
        when(noteRepository.findAll()).thenReturn(List.of(note));
        when(mapper.toDTO(note, "alice")).thenReturn(dto);

        List<NoteDTO> result = noteService.getAllNotes("alice");
        assertEquals(1, result.size());
    }

    @Test
    void getNoteById_ReturnsNoteDTO() {
        Note note = new Note();
        NoteDTO dto = new NoteDTO();
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(mapper.toDTO(note, "alice")).thenReturn(dto);

        NoteDTO result = noteService.getNoteById(1, "alice");
        assertNotNull(result);
    }

    @Test
    void addNoteFromDTO_ReturnsNoteDTO() {
        EventNoteDTO dto = new EventNoteDTO();
        dto.setTitle("Test");
        dto.setContent("Content");
        dto.setPrivacy(NotePrivacy.PRIVATE);
        dto.setRating(5);
        dto.setLatitude(1.0);
        dto.setLongitude(2.0);
        dto.setType(NoteType.EVENT);
        dto.setStart("2023-01-01T00:00:00");
        dto.setEnd("2023-01-02T00:00:00");

        User user = new User();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        Note savedNote = new Event();
        NoteDTO returnedDto = new NoteDTO();
        when(noteRepository.save(any())).thenReturn(savedNote);
        when(mapper.toDTO(savedNote, "user@example.com")).thenReturn(returnedDto);

        NoteDTO result = noteService.addNoteFromDTO(dto, "user@example.com");
        assertNotNull(result);
    }

    @Test
    void checkNote_ReturnsFalseIfNull() {
        assertFalse(noteService.checkNote(null));
    }

    @Test
    void findNotesByType_ReturnsList() {
        Note note = new Note();
        NoteDTO dto = new NoteDTO();
        when(noteRepository.findByType(NoteType.HISTORICAL)).thenReturn(List.of(note));
        when(mapper.toDTO(note, "alice")).thenReturn(dto);

        List<NoteDTO> result = noteService.findNotesByType(NoteType.HISTORICAL, "alice");
        assertEquals(1, result.size());
    }

    @Test
    void sortNoteList_ReturnsSortedList() {
        Note note = new Note();
        NoteDTO dto = new NoteDTO();
        when(noteRepository.findAll(any(Sort.class))).thenReturn(List.of(note));
        when(mapper.toDTO(note, "alice")).thenReturn(dto);

        List<NoteDTO> result = noteService.sortNoteList(true, "alice");
        assertEquals(1, result.size());
    }

    @Test
    void deleteNote_DeletesCorrectly() {
        Note note = new Note();
        note.setId(1);
        noteService.deleteNote(note);

        verify(userSavedRepository).deleteAllByNoteId(1);
        verify(userLikesNotesRepository).deleteAllByNoteId(1);
        verify(noteRepository).delete(note);
    }

    @Test
    void getNoteByIdNote_ReturnsNote() {
        Note note = new Note();
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));

        Note result = noteService.getNoteByIdNote(1);
        assertNotNull(result);
    }

    @Test
    void sortNoteListByAntiquity_ReturnsSortedList() {
        Note note = new Note();
        NoteDTO dto = new NoteDTO();
        when(noteRepository.findAll(any(Sort.class))).thenReturn(List.of(note));
        when(mapper.toDTO(note, "alice")).thenReturn(dto);

        List<NoteDTO> result = noteService.sortNoteListByAntiquity(false, "alice");
        assertEquals(1, result.size());
    }
}