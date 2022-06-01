package com.instagram.service;

import com.instagram.controller.dto.request.LoginRequestDto;
import com.instagram.controller.dto.request.PasswordRequestDto;
import com.instagram.controller.dto.request.UserRequestDto;
import com.instagram.model.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void registerUser(UserRequestDto request);
    void followUser(String username, String followerUsername);
    void unfollowUser(String username, String followerUsername);
    void updatePhoto(String username, String photoUrl);
    void changePassword(String username, PasswordRequestDto request);
    UserProjection getUserByExternalId(UUID externalId);
    UserProjection getUserByUsername(String username);
    List<UserProjection> getAllUsers();
    Page<UserProjection> getPageableUsers(Pageable pageable);
    Page<UserProjection> getUsersBySearchQuery(String query, Pageable pageable);
    Page<UserProjection> getUserFollowersByUsername(String username, Pageable pageable);
    Page<UserProjection> getUserFollowingsByUsername(String username, Pageable pageable);
    int countUserFollowings(String username);
    int countUserFollowers(String username);
    void updateUserProfileByUsername(String username, UserRequestDto request);
    void deleteUserByUsername(String username);
    Optional<String> getUserPassword(String username);
}