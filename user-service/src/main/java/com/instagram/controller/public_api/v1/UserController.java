package com.instagram.controller.public_api.v1;

import com.instagram.controller.dto.request.PasswordRequestDto;
import com.instagram.controller.dto.request.UserRequestDto;
import com.instagram.model.projection.UserProjection;
import com.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/public/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserProjection getUserByUsername(@PathVariable("username") String username) {
        log.info("Received a request to get a user by username: {}", username);
        return userService.getUserByUsername(username);
    }

    @GetMapping(path = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserProjection> getUsersBySearchQuery(@RequestParam("query") String query,
                                                      @PageableDefault(size = 20) Pageable pageable) {
        log.info("Received a request to get users by query: {}", query);
        return userService.getUsersBySearchQuery(query, pageable);
    }

    @GetMapping(path = "/{username}/followers")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserProjection> getUserFollowers(@PathVariable("username") String username,
                                                 @PageableDefault(size = 20) Pageable pageable) {
        log.info("Received a request to return followers of '{}'", username);
        return userService.getUserFollowersByUsername(username, pageable);
    }

    @GetMapping(path = "/{username}/followings")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserProjection> getUserFollowings(@PathVariable("username") String username,
                                                  @PageableDefault(size = 20) Pageable pageable) {
        log.info("Received a request to return followings of '{}'", username);
        return userService.getUserFollowingsByUsername(username, pageable);
    }

    @GetMapping(path = "/{username}/followers/count")
    @ResponseStatus(HttpStatus.OK)
    public int getUserFollowersCount(@PathVariable("username") String username) {
        log.info("Received a request to get a count of '{}' followers", username);
        return userService.countUserFollowers(username);
    }

    @GetMapping(path = "/{username}/followings/count")
    @ResponseStatus(HttpStatus.OK)
    public int getUserFollowingsCount(@PathVariable("username") String username) {
        log.info("Received a request to get a count of '{}' followings", username);
        return userService.countUserFollowings(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid UserRequestDto request) {
        log.info("Received a request to register a user: {}", request);
        userService.registerUser(request);
    }

    @PostMapping(path = "/{username}/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable("username") String username, @RequestBody @Valid PasswordRequestDto request) {
        log.info("Received a request to change a password on: {}", request);
        userService.changePassword(username, request);
    }

    @PutMapping(path = "/{username}/followers/{followerUsername}")
    @ResponseStatus(HttpStatus.OK)
    public void follow(@PathVariable("username") String username,
                       @PathVariable("followerUsername") String followerUsername) {
        log.info("Received a request to add a follower with id {} for user with id {}", username, followerUsername);
        userService.followUser(username, followerUsername);
    }

    @DeleteMapping(path = "/{username}/followers/{followerUsername}")
    @ResponseStatus(HttpStatus.OK)
    public void unfollow(@PathVariable("username") String username,
                         @PathVariable("followerUsername") String followerUsername) {
        log.info("Received a request to add a follower with id {} for user with id {}", username, followerUsername);
        userService.unfollowUser(username, followerUsername);
    }

    @PutMapping(path = "/{username}/photo")
    @ResponseStatus(HttpStatus.OK)
    public void updatePhoto(@PathVariable("username") String username,
                            @RequestParam("photo-url") String photoUrl) {
        log.info("Received a request to update a photo {} for user with username {} ", photoUrl, username);
        userService.updatePhoto(username, photoUrl);
    }

    @PutMapping(path = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProfileByUsername(@PathVariable("username") String username,
                                        @RequestBody @Valid UserRequestDto request) {
        log.info("Received a request to update user by username: {}, {}", username, request);
        userService.updateUserProfileByUsername(username, request);
    }

    @DeleteMapping(path = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByUsername(@PathVariable("username") String username) {
        log.info("Received a request to delete a user by username: {}", username);
        userService.deleteUserByUsername(username);
    }
}