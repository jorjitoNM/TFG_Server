package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.domain.service.NoteService;
import org.server.domain.service.UserService;
import org.server.ui.model.NoteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotasController {

    private final NoteService noteService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getNotes() {
        return ResponseEntity.ok(noteService.getAllNotes(SecurityContextHolder.getContext().getAuthentication().getName()));
    }


    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable int noteId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId,SecurityContextHolder.getContext().getAuthentication().getName()));
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
        NoteDTO updatedNote = noteService.updateNoteFromDTO(noteDTO,SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping("/saves")
    public ResponseEntity<List<NoteDTO>> getSavedNotes() {
        List<NoteDTO> savedNotes = userService.getSavedNotesForUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(savedNotes);
    }

    @DeleteMapping("/saves")
    public ResponseEntity<Void> deleteSavedNote(@RequestParam int noteId) {
        userService.removeSavedNotesForUser(SecurityContextHolder.getContext().getAuthentication().getName(), noteId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/liked")
    public ResponseEntity<Void> deleteLikedNote(@RequestParam int noteId) {
        userService.removeLikedNotesForUser(SecurityContextHolder.getContext().getAuthentication().getName(), noteId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rate")
    public ResponseEntity<NoteDTO> rateNote(
            @PathVariable int id,
            @RequestParam int rating
    ) {
        NoteDTO ratedNote = noteService.rateNoteAndReturnDTO(id, rating,SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(ratedNote);
    }


    @PostMapping("/addNota")
    public ResponseEntity<NoteDTO> addNote(
            @RequestBody NoteDTO noteDTO
    ) {
        NoteDTO createdNote = noteService.addNoteFromDTO(noteDTO, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(createdNote);
    }


    @GetMapping("/type")
    public ResponseEntity<List<NoteDTO>> getNotesByType(@RequestParam NoteType type) {
        List<NoteDTO> notes = noteService.findNotesByType(type,SecurityContextHolder.getContext().getAuthentication().getName());
        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable int id) {
        Note note = noteService.getNoteByIdNote(id);
        noteService.deleteNote(note);
        return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<NoteDTO>> getNotesSortedByLikes(
            @RequestParam boolean ascending) {
        List<NoteDTO> sortedNotes = noteService.sortNoteList(ascending);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(sortedNotes);
    }

    @GetMapping("/antiquity")
    public ResponseEntity<List<NoteDTO>> getNotesSortedByAntiquity(
            @RequestParam boolean ascending) {
        List<NoteDTO> sortedNotes = noteService.sortNoteListByAntiquity(ascending);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(sortedNotes);
    }
}
