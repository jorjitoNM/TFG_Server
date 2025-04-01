package org.server.ui.controllers;


import lombok.RequiredArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.domain.service.NoteService;
import org.server.ui.model.NoteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/notes")
@RequiredArgsConstructor
public class NotasController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable int noteId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId));
    }

    @GetMapping("/area")
    public ResponseEntity<List<Note>> getNotesByGeographicArea(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5.0") double radiusKm
    ) {

        List<Note> notes = noteService.findNotesByGeographicArea(latitude, longitude, radiusKm);

        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notes);
    }

    @PutMapping
    public ResponseEntity<Note> updateNote(
            @RequestBody Note note,
            @RequestHeader("X-Username") String username
    ) {
        Note updatedNote = noteService.updateNote(note, username);
        return ResponseEntity.ok(updatedNote);
    }



    @PatchMapping("/{id}/rate")
    public ResponseEntity<Note> rateNote(
            @PathVariable int id,
            @RequestParam int rating,
            @RequestHeader("X-Username") String username
    ) {
        Note ratedNote = noteService.rateNote(id, rating, username);
        return ResponseEntity.ok(ratedNote);
    }
}
