package org.server.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.server.common.Mapper;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserFollowed;
import org.server.dao.model.user.UserFollower;
import org.server.dao.repositories.*;
import org.server.domain.errors.AutoFollowException;
import org.server.domain.errors.NoValidUserException;
import org.server.ui.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final UserFollowedRepository userFollowedRepository;
    private final UserFollowerRepository userFollowerRepository;
    private final Mapper mapper;

    public void followUser(String followerUsername, String followedUsername) {
        if (followerUsername.equals(followedUsername)) throw new AutoFollowException("No puedes seguirte a ti mismo");

        User follower = userRepository.findByOwnUsername(followerUsername);
        User followed = userRepository.findByOwnUsername(followedUsername);
        if (follower == null || followed == null) throw new NoValidUserException("Usuario no válido");

        if (!userFollowedRepository.existsByOwnerAndFollowed(follower, followed)) {
            userFollowedRepository.save(new UserFollowed(0, follower, followed));
            userFollowerRepository.save(new UserFollower(0, followed, follower));
        }
    }
    @Transactional
    public void unfollowUser(String followerUsername, String followedUsername) {
        User follower = userRepository.findByOwnUsername(followerUsername);
        User followed = userRepository.findByOwnUsername(followedUsername);
        if (follower == null || followed == null) throw new NoValidUserException("Usuario no válido");

        userFollowedRepository.deleteByOwnerAndFollowed(follower, followed);
        userFollowerRepository.deleteByOwnerAndFollower(followed, follower);
    }

    public List<UserDTO> getFollowers(String username) {
        User user = userRepository.findByOwnUsername(username);
        return userFollowerRepository.findAllByOwner(user)
                .stream()
                .map(uf -> mapper.toUserDTO(uf.getFollower()))
                .toList();
    }


    public List<UserDTO> getFollowing(String username) {
        User user = userRepository.findByOwnUsername(username);
        return userFollowedRepository.findAllByOwner(user)
                .stream()
                .map(uf -> mapper.toUserDTO(uf.getFollowed()))
                .toList();
    }

    public boolean isFollowing(String followerUsername, String followedUsername) {
        User follower = userRepository.findByOwnUsername(followerUsername);
        User followed = userRepository.findByOwnUsername(followedUsername);
        if (follower == null || followed == null) return false;
        return userFollowedRepository.existsByOwnerAndFollowed(follower, followed);
    }
}
