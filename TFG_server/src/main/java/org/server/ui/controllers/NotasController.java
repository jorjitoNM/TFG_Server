package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.NoteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notes")
@RequiredArgsConstructor
public class NotasController {

    private final NoteService noteService;


}
