package com.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableWebSecurity()
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new GrantsConverter());
        return http
                .authorizeRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/public/v1/users").permitAll()
                        .antMatchers("/public/v1/**").hasRole("USER")
                        //.antMatchers(HttpMethod.GET, "/public/v1/**").hasAuthority("SCOPE_read")
                        //.antMatchers(HttpMethod.POST, "/public/v1/**").hasAuthority("SCOPE_modify")
                        //.antMatchers(HttpMethod.PUT, "/public/v1/**").hasAuthority("SCOPE_modify")
                        //.antMatchers(HttpMethod.DELETE, "/public/v1/**").hasAuthority("SCOPE_modify")
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(customizer -> customizer.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter)).build();
    }

    @SuppressWarnings("unchecked")
    public static class GrantsConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        @Override
        public Collection<GrantedAuthority> convert(Jwt source) {
            Map<String, Object> claims = source.getClaims();
            List<String> authorities = (List<String>) claims.get("authorities");
            if (!CollectionUtils.isEmpty(authorities)) {
                return authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
            }

            return Collections.EMPTY_LIST;
        }
    }
}