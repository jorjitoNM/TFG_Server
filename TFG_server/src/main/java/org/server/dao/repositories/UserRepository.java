package org.server.dao.repositories;

import org.server.dao.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //Find user by username
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);

}
