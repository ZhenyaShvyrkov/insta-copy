package com.instagram.repository;

import com.instagram.model.entity.User;
import com.instagram.model.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.isDeleted = false AND u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT name, username, photo_url AS photoUrl, email, bio, website, phone_number AS phoneNumber, gender, " +
            "(SELECT COUNT(*) FROM users_followers WHERE target_id = u.id) AS followersNumber, " +
            "(SELECT COUNT(*) FROM users_followers WHERE follower_id = u.id) AS followingsNumber " +
            "FROM users u WHERE u.username = ?1", nativeQuery = true)
    Optional<UserProjection> findUserProjectionByUsernameWithFollows(String username);

    @Query(value = "SELECT name, username, photo_url AS photoUrl, email, bio, website, phone_number AS phoneNumber, gender " +
            "FROM users u WHERE u.username = ?1", nativeQuery = true)
    Optional<UserProjection> findUserProjectionByUsername(String username);

    @Query(value = "SELECT name, username, photo_url AS photoUrl, email, bio, website, phone_number AS phoneNumber, gender " +
            "FROM users u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.name) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    Page<UserProjection> findBySearchQuery(String query, Pageable pageable);

    @Query(value = "SELECT name, username, photo_url AS photoUrl, email, bio, website, phone_number AS phoneNumber, gender " +
            "FROM users u WHERE u.username = ?1", nativeQuery = true)
    Page<UserProjection> findUserFollowers(String username, Pageable pageable);

    @Query(value = "SELECT name, username, photo_url AS photoUrl, email, bio, website, phone_number AS phoneNumber, gender FROM users u " +
            "WHERE id IN (SELECT target_id FROM users_followers " +
            "WHERE follower_id = (SELECT id FROM users WHERE username = ?1))", nativeQuery = true)
    Page<UserProjection> findUserFollowings(String username, Pageable pageable);

    @Query(value = "SELECT CASE WHEN COUNT(u) = 1 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = ?1")
    boolean existsByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM users_followers WHERE target_id = (SELECT id FROM users u WHERE u.username = ?1)", nativeQuery = true)
    int countUserFollowers(String username);

    @Query(value = "SELECT COUNT(*) FROM users_followers WHERE follower_id = (SELECT id FROM users u WHERE u.username = ?1)", nativeQuery = true)
    int countUserFollowings(String username);

    @Modifying
    @Query(value = "INSERT INTO users_followers(target_id, follower_id) VALUES (" +
            "(SELECT id FROM users WHERE username = ?1), (SELECT id FROM users WHERE username = ?2))", nativeQuery = true)
    void addFollower(String username, String followerUsername);

    @Modifying
    @Query(value = "DELETE FROM users_followers uf " +
            "WHERE target_id = (SELECT id FROM users WHERE username = ?1) " +
            "AND follower_id = (SELECT id FROM users WHERE username = ?2)", nativeQuery = true)
    void removeFollower(String username, String followerUsername);

    @Modifying
    @Query(value = "UPDATE User u SET u.photoUrl = ?2, u.modifiedAt = CURRENT_TIMESTAMP WHERE u.username = ?1")
    void updatePhoto(String username, String photoUrl);

    @Modifying
    @Query(value = "UPDATE User u SET u.password = ?2, u.modifiedAt = CURRENT_TIMESTAMP WHERE u.username = ?1")
    void updatePassword(String username, String password);

    @Modifying
    @Query(value = "UPDATE User u SET u.isDeleted = true, u.modifiedAt = CURRENT_TIMESTAMP WHERE u.username = ?1")
    void deleteUser(String username);


    //<-------------------Keycloak-------------------->

    @Query(value = "SELECT name, username, photo_url AS photoUrl, email, bio, website, phone_number AS phoneNumber, gender, role " +
            "FROM users u WHERE u.is_deleted = false AND u.external_id = ?1", nativeQuery = true)
    Optional<UserProjection> findByExternalId(UUID externalId);

    @Query(value = "SELECT name, username, email, phone_number AS phoneNumber, gender FROM users u ", nativeQuery = true)
    Page<UserProjection> findPageableUsers(Pageable pageable);

    @Query(value = "SELECT name, username, photo_url AS photoUrl, email, bio, website, phone_number AS phoneNumber, gender, role FROM users u", nativeQuery = true)
    List<UserProjection> findAllUsers();

    @Query(value = "SELECT u.password FROM users u WHERE u.username = ?1", nativeQuery = true)
    Optional<String> findPassword(String username);
}