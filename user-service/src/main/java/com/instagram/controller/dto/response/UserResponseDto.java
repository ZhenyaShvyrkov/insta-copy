package com.instagram.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.instagram.model.Gender;
import com.instagram.model.entity.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    String name;
    String username;
    String photoUrl;
    String bio;
    String website;
    String email;
    String phoneNumber;
    Gender gender;
    Integer followersNumber;
    Integer followingsNumber;


    public static UserResponseDto createUserResponseDto(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .photoUrl(user.getPhotoUrl())
                .bio(user.getBio())
                .email(user.getEmail())
                .website(user.getWebsite())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .build();
    }

    public static UserResponseDto createUserResponseDto(User user, int followersNumber, int followingsNumber) {
        return UserResponseDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .photoUrl(user.getPhotoUrl())
                .bio(user.getBio())
                .email(user.getEmail())
                .website(user.getWebsite())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .followersNumber(followersNumber)
                .followingsNumber(followingsNumber)
                .build();
    }
}