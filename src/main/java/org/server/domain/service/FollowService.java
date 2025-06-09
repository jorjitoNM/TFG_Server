package org.server.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.server.common.Mapper;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserFollowed;
import org.server.dao.model.user.UserFollower;
import org.server.dao.repositories.UserFollowedRepository;
import org.server.dao.repositories.UserFollowerRepository;
import org.server.dao.repositories.UserRepository;
import org.server.domain.errors.AutoFollowException;
import org.server.domain.errors.NoValidUserException;
import org.server.domain.errors.UserNotFoundException;
import org.server.ui.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final UserFollowedRepository userFollowedRepository;
    private final UserFollowerRepository userFollowerRepository;
    private final Mapper mapper;

    public void followUser(String followerEmail, String followedEmail) {
        if (followerEmail.equals(followedEmail)) throw new AutoFollowException("No puedes seguirte a ti mismo");

        Optional<User> follower = userRepository.findByEmail(followerEmail);
        Optional<User> followed = userRepository.findByEmail(followedEmail);
        if (follower.isEmpty() || followed.isEmpty()) throw new NoValidUserException("Usuario no válido");

        if (!userFollowedRepository.existsByOwnerAndFollowed(follower.get(), followed.get())) {
            userFollowedRepository.save(new UserFollowed(0, follower.get(), followed.get()));
            userFollowerRepository.save(new UserFollower(0, followed.get(), follower.get()));
        }
    }
    @Transactional
    public void unfollowUser(String followerEmail, String followedEmail) {
        Optional<User> follower = userRepository.findByEmail(followerEmail);
        Optional<User> followed = userRepository.findByEmail(followedEmail);
        if (follower.isEmpty() || followed.isEmpty()) throw new NoValidUserException("Usuario no válido");

        userFollowedRepository.deleteByOwnerAndFollowed(follower.get(), followed.orElse(null));
        userFollowerRepository.deleteByOwnerAndFollower(followed.get(), follower.get());
    }

    public List<UserDTO> getFollowers(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return userFollowerRepository.findAllByOwner(user)
                .stream()
                .map(uf -> mapper.toUserDTO(uf.getFollower()))
                .toList();
    }


    public List<UserDTO> getFollowing(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return userFollowedRepository.findAllByOwner(user)
                .stream()
                .map(uf -> mapper.toUserDTO(uf.getFollowed()))
                .toList();
    }

    public boolean isFollowing(String followerEmail, String followedEmail) {
        User follower = userRepository.findByEmail(followerEmail).orElseThrow(UserNotFoundException::new);
        User followed = userRepository.findByEmail(followedEmail).orElseThrow(UserNotFoundException::new);
        if (follower == null || followed == null) return false;
        return userFollowedRepository.existsByOwnerAndFollowed(follower, followed);
    }
}
