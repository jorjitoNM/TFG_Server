package org.server.dao.repositories;

import org.server.dao.model.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByOrderByLikesDesc();

    List<Note> findAllByOrderByLikesAsc();

    Optional<Note> findById(Long id);
}
