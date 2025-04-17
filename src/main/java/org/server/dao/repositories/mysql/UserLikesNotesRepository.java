package org.server.dao.repositories.mysql;

import org.server.dao.model.user.UserLikedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikesNotesRepository extends JpaRepository<UserLikedNote,Integer> {
}
