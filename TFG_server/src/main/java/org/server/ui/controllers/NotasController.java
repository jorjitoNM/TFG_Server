package org.server.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.domain.service.NoteService;
import org.server.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notes")
@RequiredArgsConstructor
public class NotasController {

    private final NoteService noteService;
    private final UserService userService;

    @GetMapping("/sorted")
    public ResponseEntity<List<Note>> getNotesSortedByLikes(
            @RequestParam boolean ascending) {
        List<Note> sortedNotes = noteService.sortNoteList(ascending);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(sortedNotes);
    }

    @GetMapping("/saveds")
    public ResponseEntity<List<Note>> getSavedNotes(
            @RequestParam String username) {
        List<Note> savedNotes = userService.getSavedNotesForUser(username);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(savedNotes);
    }

    @PostMapping("/saveds")
    public ResponseEntity<Boolean> addNoteToSaved(
            @RequestParam String username,
            @RequestParam Long noteId) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userService.addNoteToSaved(username, noteId));
    }
}
