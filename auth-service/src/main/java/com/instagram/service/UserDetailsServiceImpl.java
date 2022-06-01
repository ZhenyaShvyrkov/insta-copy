package com.instagram.service;

import com.instagram.model.internal.CustomUserDetails;
import com.instagram.model.projection.AccountInfo;
import com.instagram.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    public static final String ROLE_PREFIX = "ROLE_";

    private final UserDetailsRepository repository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountInfo accountInfo = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));
        return new CustomUserDetails(
                accountInfo.getUsername(),
                accountInfo.getPassword(),
                Set.of(new SimpleGrantedAuthority(ROLE_PREFIX.concat(accountInfo.getRole()))),
                accountInfo.getExternalId());
    }
}
