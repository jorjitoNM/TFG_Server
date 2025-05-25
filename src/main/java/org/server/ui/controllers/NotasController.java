package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.server.domain.service.ImagesService;
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

    private final ImagesService imagesService;
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
        NoteDTO updatedNote = noteService.updateNoteFromDTO(noteDTO);
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping("/saveds")
    public ResponseEntity<List<NoteDTO>> getSavedNotes(
            @RequestParam String username) {
        List<NoteDTO> savedNotes = userService.getSavedNotesForUser(username);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(savedNotes);
    }

    @DeleteMapping("/saveds")
    public ResponseEntity<Void> deleteSavedNote(@RequestParam int noteId) {
        userService.removeSavedNotesForUser("user1", noteId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/liked")
    public ResponseEntity<Void> deleteLikedNote(@RequestParam int noteId) {
        userService.removeLikedNotesForUser("user1", noteId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rate")
    public ResponseEntity<NoteDTO> rateNote(
            @PathVariable int id,
            @RequestParam int rating
    ) {
        NoteDTO ratedNote = noteService.rateNoteAndReturnDTO(id, rating);
        return ResponseEntity.ok(ratedNote);
    }


    @PostMapping("/addNota")
    public ResponseEntity<Note> addNote(
            @RequestBody Note note
    ) {
        Note createdNote = noteService.addNote(note, SecurityContextHolder.getContext().getAuthentication().getName());
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
}
