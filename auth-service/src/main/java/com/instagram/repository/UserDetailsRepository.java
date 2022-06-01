package com.instagram.repository;

import com.instagram.model.User;
import com.instagram.model.projection.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT CAST(external_id AS VARCHAR) AS externalId, username, password, role FROM users " +
            "WHERE is_deleted = false AND username = ?1", nativeQuery = true)
    Optional<AccountInfo> findByUsername(String username);
}
