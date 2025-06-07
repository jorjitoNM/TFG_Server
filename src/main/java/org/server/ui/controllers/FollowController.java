package org.server.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.server.domain.service.FollowService;
import org.server.ui.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService userFollowService;

    @PostMapping("/follow")
    public ResponseEntity<Void> followUser(@RequestParam String username) {
        String followerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        userFollowService.followUser(followerUsername, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<Void> unfollowUser(@RequestParam String username) {
        String followerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        userFollowService.unfollowUser(followerUsername, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable String username) {
        return ResponseEntity.ok(userFollowService.getFollowers(username));
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<List<UserDTO>> getFollowing(@PathVariable String username) {
        return ResponseEntity.ok(userFollowService.getFollowing(username));
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserDTO>> getMyFollowers() {
        return ResponseEntity.ok(userFollowService.getFollowers(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping("/following")
    public ResponseEntity<List<UserDTO>> getMyFollowing() {
        return ResponseEntity.ok(userFollowService.getFollowing(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @GetMapping("/is-following")
    public ResponseEntity<Boolean> isFollowing(@RequestParam String username) {
        String followerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userFollowService.isFollowing(followerUsername, username));
    }
}
