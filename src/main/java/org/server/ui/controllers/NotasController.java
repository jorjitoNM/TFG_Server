package org.server.ui.controllers;


import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.note.Note;
import org.server.dao.repositories.mongodb.ImageRepository;
import org.server.domain.service.ImagesService;
import org.server.domain.service.NoteService;
import org.server.ui.model.NoteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/notes")
@RequiredArgsConstructor
public class NotasController {

    private final ImagesService imagesService;
    private final NoteService noteService;

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

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable int id,
            @RequestBody Note note,
            @RequestHeader(Constantes.X_USERNAME) String username
    ) {
        Note updatedNote = noteService.updateNote(id, note, username);
        return ResponseEntity.ok(updatedNote);
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

    @PostMapping("/addNota")
    public ResponseEntity<Note> addNote(
            @RequestBody Note note,
            @RequestHeader(Constantes.X_USERNAME) String username
    ) {
        Note createdNote = noteService.addNote(note, username);
        return ResponseEntity.ok(createdNote);
    }

}
