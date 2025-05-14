package org.server.dao.repositories;

import org.server.dao.model.note.Note;
import org.server.dao.model.note.NoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByOrderByLikesDesc();

    List<Note> findAllByOrderByLikesAsc();

    @Query("SELECT n FROM Note n WHERE n.latitude BETWEEN :minLat AND :maxLat AND n.longitude BETWEEN :minLng AND :maxLng AND n.privacy = 'PUBLIC'")
    List<Note> findNotesByGeographicArea(@Param("minLat") double minLatitude, @Param("maxLat") double maxLatitude, @Param("minLng") double minLongitude, @Param("maxLng") double maxLongitude);

    @Query("SELECT n FROM Note n WHERE n.id = :noteId AND n.owner.username = :username")
    Optional<Note> findByIdAndOwnerUsername(@Param("noteId") int noteId, @Param("username") String username);

    @Query("SELECT n FROM Note n WHERE n.type = :note_type")
    List<Note> findByType(@Param("note_type") NoteType noteType);



}
