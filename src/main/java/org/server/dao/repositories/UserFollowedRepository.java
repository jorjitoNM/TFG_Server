package org.server.dao.repositories;

import org.server.dao.model.user.User;
import org.server.dao.model.user.UserFollowed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowedRepository extends JpaRepository<UserFollowed, Integer> {
    boolean existsByOwnerAndFollowed(User owner, User followed);
    void deleteByOwnerAndFollowed(User owner, User followed);
    List<UserFollowed> findAllByOwner(User owner);
    List<UserFollowed> findByOwnerUsername(String username);
}