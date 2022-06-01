package com.instagram.service.impl;

import com.instagram.controller.dto.request.LoginRequestDto;
import com.instagram.controller.dto.request.PasswordRequestDto;
import com.instagram.controller.dto.request.UserRequestDto;
import com.instagram.model.Gender;
import com.instagram.model.entity.Role;
import com.instagram.model.entity.User;
import com.instagram.model.projection.UserProjection;
import com.instagram.repository.UserRepository;
import com.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "User with username '%s' wasn't found";

    private final UserRepository userRepository;

    @Override
    public void registerUser(UserRequestDto request) {
        checkIfUsernameDoNotExist(request.getUsername());
        User user = request.createUserFromRequest();
        user.setExternalId(UUID.randomUUID());
        user.setRole(Role.USER);
        userRepository.save(user);
        // set password encoded
        // send notification
    }


    @Override
    @Transactional(readOnly = true)
    public UserProjection getUserByUsername(String username) {
        return userRepository.findUserProjectionByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }

    @Override
    public List<UserProjection> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Page<UserProjection> getPageableUsers(Pageable pageable) {
        return userRepository.findPageableUsers(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProjection> getUsersBySearchQuery(String query, Pageable pageable) {
        return userRepository.findBySearchQuery(query, pageable);
    }

    @Override
    public Page<UserProjection> getUserFollowersByUsername(String username, Pageable pageable) {
        return userRepository.findUserFollowers(username, pageable);
    }

    @Override
    public Page<UserProjection> getUserFollowingsByUsername(String username, Pageable pageable) {
        return userRepository.findUserFollowings(username, pageable);
    }

    @Override
    public int countUserFollowings(String username) {
        return userRepository.countUserFollowings(username);
    }

    @Override
    public int countUserFollowers(String username) {
        return userRepository.countUserFollowers(username);
    }

    @Override
    public void followUser(String username, String followerUsername) {
        userRepository.addFollower(username, followerUsername);
    }

    @Override
    public void unfollowUser(String username, String followerUsername) {
        userRepository.removeFollower(username, followerUsername);
    }

    @Override
    public void updatePhoto(String username, String photoUrl) {
        userRepository.updatePhoto(username, photoUrl);
        //send notification
    }

    @Override
    public void changePassword(String username, PasswordRequestDto request) {
        //TODO encode password
        userRepository.updatePassword(username, request.getPassword());
    }

    @Override
    public UserProjection getUserByExternalId(UUID externalId) {
        return userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %s not found", externalId)));
    }

    @Override
    public void updateUserProfileByUsername(String username, UserRequestDto request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));

        replaceUsernameIfNewAndOldAreDifferent(user, request.getUsername());
        replaceEmailIfNewAndOldAreDifferent(user, request.getEmail()); // TODO Send notification on the new email
        user.setName(request.getName());
        user.setBio(request.getBio());
        user.setWebsite(request.getWebsite());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(Gender.findGenderByGivenKey(request.getGender()));
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepository.deleteUser(username);
    }

    @Override
    public Optional<String> getUserPassword(String username) {
        return userRepository.findPassword(username);
    }

    private void checkIfUsernameDoNotExist(String username) {
        if (userRepository.existsByUsername(username)) {
            log.error("Username is already taken: {}", username);
            throw new ValidationException(String.format("This username '%s' already exists", username));
        }
    }

    private void replaceUsernameIfNewAndOldAreDifferent(User user, String newUsername) {
        if (!user.getUsername().equals(newUsername)) {
            checkIfUsernameDoNotExist(newUsername);
            user.setUsername(newUsername);
        }
    }

    private void replaceEmailIfNewAndOldAreDifferent(User user, String newEmail) {
        if (!user.getEmail().equals(newEmail)) {
            user.setEmail(newEmail);
        }
    }
}