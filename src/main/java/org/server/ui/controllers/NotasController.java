package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.domain.service.NoteService;
import org.server.domain.service.UserService;
import org.server.ui.model.NoteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotasController {



    private final NoteService noteService;
    private final UserService userService;

    @GetMapping("/area")
    public ResponseEntity<List<NoteDTO>> getNotesByGeographicArea(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5.0") double radiusKm
    ) {

        List<NoteDTO> notes = noteService.findNotesByGeographicArea(latitude, longitude, radiusKm);

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
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable int id,
            @RequestBody Note note,
            @RequestHeader(Constantes.X_USERNAME) String username
    ) {
        Note updatedNote = noteService.updateNote(id, note, username);
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping("/saveds")
    public ResponseEntity<List<Note>> getSavedNotes(
            @RequestParam String username) {
        List<Note> savedNotes = userService.getSavedNotesForUser(username);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(savedNotes);
    }
    @PatchMapping("/{id}/rate")
    public ResponseEntity<Note> rateNote(
            @PathVariable int id,
            @RequestParam int rating,
            @RequestHeader(Constantes.X_USERNAME) String username
    ) {
        Note ratedNote = noteService.rateNote(id, rating, username);
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
    public ResponseEntity<List<Note>> getNotesByType(@RequestParam NoteType type) {
        List<Note> notes = noteService.findNotesByType(type);

        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notes);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable int id) {
        Note note=noteService.getNoteById(id);
        return noteService.deleteNote(note) ? ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build()
                : ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
    }
}
