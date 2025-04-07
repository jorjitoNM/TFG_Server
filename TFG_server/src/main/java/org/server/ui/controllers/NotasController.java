package org.server.ui.controllers;


import lombok.RequiredArgsConstructor;
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

    @PatchMapping("/{id}/rate")
    public ResponseEntity<NoteDTO> rateNote(
            @PathVariable int id,
            @RequestParam int rating
    ) {
        NoteDTO ratedNote = noteService.rateNoteAndReturnDTO(id, rating);
        return ResponseEntity.ok(ratedNote);
    }
}