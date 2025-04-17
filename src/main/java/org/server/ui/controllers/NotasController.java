package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.note.Note;
import org.server.domain.service.NoteService;
import org.server.ui.model.NoteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotasController {

    private final ImagesService imagesService;
    private final NoteService noteService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable int noteId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId));
    }

    @GetMapping("/area")
    public ResponseEntity<List<NoteDTO>> getNotesByGeographicArea(
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {
        List<NoteDTO> notes = noteService.findNotesByGeographicArea(latitude, longitude);
        return ResponseEntity.ok(notes);
    }

    @PutMapping
    public ResponseEntity<NoteDTO> updateNote(
            @RequestBody NoteDTO noteDTO
    ) {
        NoteDTO updatedNote = noteService.updateNoteFromDTO(noteDTO);
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping("/saveds")
    public ResponseEntity<List<Note>> getSavedNotes(
            @RequestParam String username) {
        List<Note> savedNotes = userService.getSavedNotesForUser(username);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(savedNotes);
    }
    @PatchMapping("/{id}/rate")
    public ResponseEntity<NoteDTO> rateNote(
            @PathVariable int id,
            @RequestParam int rating
    ) {
        NoteDTO ratedNote = noteService.rateNoteAndReturnDTO(id, rating);
        return ResponseEntity.ok(ratedNote);
    }

    @PostMapping("/saveds")
    public ResponseEntity<Boolean> addNoteToSaved(
            @RequestParam String username,
            @RequestParam int noteId) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userService.addNoteToSaved(username, noteId));
    }

    @PostMapping("/addNota")
    public ResponseEntity<Note> addNote(
            @RequestBody Note note,
            @RequestHeader(Constantes.X_USERNAME) String username
    ) {
        Note createdNote = noteService.addNote(note, username);
        return ResponseEntity.ok(createdNote);
    }

    @GetMapping("/type")
    public ResponseEntity<List<NoteDTO>> getNotesByType(@RequestParam NoteType type) {
        List<NoteDTO> notes = noteService.findNotesByType(type);

        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notes);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Note>> getNotesSortedByLikes(
            @RequestParam boolean ascending) {
        List<Note> sortedNotes = noteService.sortNoteList(ascending);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(sortedNotes);
    }
}
