package org.server.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FollowServiceTest {
    @InjectMocks
    private FollowService followService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFollowedRepository userFollowedRepository;

    @Mock
    private UserFollowerRepository userFollowerRepository;

    @Mock
    private Mapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void followUser_success() {
        User follower = new User();
        follower.setEmail("follower@example.com");
        User followed = new User();
        followed.setEmail("followed@example.com");

        when(userRepository.findByEmail("follower@example.com")).thenReturn(Optional.of(follower));
        when(userRepository.findByEmail("followed@example.com")).thenReturn(Optional.of(followed));
        when(userFollowedRepository.existsByOwnerAndFollowed(follower, followed)).thenReturn(false);

        followService.followUser("follower@example.com", "followed@example.com");

        verify(userFollowedRepository).save(any(UserFollowed.class));
        verify(userFollowerRepository).save(any(UserFollower.class));
    }

    @Test
    void followUser_selfFollow_throwsException() {
        assertThrows(AutoFollowException.class, () ->
                followService.followUser("user@example.com", "user@example.com")
        );
    }

    @Test
    void followUser_userNotFound_throwsException() {
        when(userRepository.findByEmail("follower@example.com")).thenReturn(Optional.empty());

        assertThrows(NoValidUserException.class, () ->
                followService.followUser("follower@example.com", "followed@example.com")
        );
    }

    @Test
    void unfollowUser_success() {
        User follower = new User();
        follower.setEmail("f1@example.com");
        User followed = new User();
        followed.setEmail("f2@example.com");

        when(userRepository.findByEmail("f1@example.com")).thenReturn(Optional.of(follower));
        when(userRepository.findByEmail("f2@example.com")).thenReturn(Optional.of(followed));

        followService.unfollowUser("f1@example.com", "f2@example.com");

        verify(userFollowedRepository).deleteByOwnerAndFollowed(follower, followed);
        verify(userFollowerRepository).deleteByOwnerAndFollower(followed, follower);
    }

    @Test
    void getFollowers_success() {
        User user = new User();
        user.setEmail("test@example.com");

        User follower = new User();
        follower.setEmail("follower@example.com");

        UserFollower uf = new UserFollower(0, user, follower);
        UserDTO dto = new UserDTO();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userFollowerRepository.findAllByOwner(user)).thenReturn(List.of(uf));
        when(mapper.toUserDTO(follower)).thenReturn(dto);

        List<UserDTO> result = followService.getFollowers("test@example.com");

        assertEquals(1, result.size());
        verify(mapper).toUserDTO(follower);
    }

    @Test
    void getFollowing_success() {
        User user = new User();
        user.setEmail("test@example.com");

        User followed = new User();
        followed.setEmail("followed@example.com");

        UserFollowed uf = new UserFollowed(0, user, followed);
        UserDTO dto = new UserDTO();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userFollowedRepository.findAllByOwner(user)).thenReturn(List.of(uf));
        when(mapper.toUserDTO(followed)).thenReturn(dto);

        List<UserDTO> result = followService.getFollowing("test@example.com");

        assertEquals(1, result.size());
        verify(mapper).toUserDTO(followed);
    }

    @Test
    void isFollowing_success() {
        User follower = new User();
        User followed = new User();

        when(userRepository.findByEmail("a@example.com")).thenReturn(Optional.of(follower));
        when(userRepository.findByEmail("b@example.com")).thenReturn(Optional.of(followed));
        when(userFollowedRepository.existsByOwnerAndFollowed(follower, followed)).thenReturn(true);

        assertTrue(followService.isFollowing("a@example.com", "b@example.com"));
    }

    @Test
    void isFollowing_userNotFound_throwsException() {
        when(userRepository.findByEmail("a@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                followService.isFollowing("a@example.com", "b@example.com")
        );
    }
}