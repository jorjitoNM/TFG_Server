package org.server.dao.repositories;

import org.server.dao.model.user.User;
import org.server.dao.model.user.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Integer> {
    boolean existsByOwnerAndFollower(User owner, User follower);
    void deleteByOwnerAndFollower(User owner, User follower);
    List<UserFollower> findAllByOwner(User owner);
}