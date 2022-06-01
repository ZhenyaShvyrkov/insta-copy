package com.instagram.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.instagram.model.Gender;
import com.instagram.model.entity.User;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestDto {
    @NotBlank(message = "Name can not be empty or null")
    @Size(max = 64, message = "Name length should be less than 64")
    String name;

    @NotBlank(message = "Username can not be empty or null")
    @Size(min = 6, max = 64, message = "Username length should be greater than 6 and less than 64")
    String username;

    @NotBlank(message = "Password can not be empty or null")
    @Size(min = 6, max = 32, message = "Password length should be greater than 6 and less than 32")
    String password;

    String photoUrl;

    @Size(max = 512, message = "Bio length should be less than 512")
    String bio;

    String website;

    @Email
    @Size(min = 7, max = 128, message = "Email length should be greater than 7 and less than 128")
    String email;

    @Pattern(regexp = "[0-9]\\([0-9]{3}\\)-[0-9]{3}-[0-9]{2}-[0-9]{2}|\\d+")
    @Size(min = 6, max = 32, message = "Phone number length should be greater than 6 and less than 32")
    String phoneNumber;

    String gender;

    public User createUserFromRequest() {
        return User.builder()
                .email(email)
                .username(username)
                .password(password)
                .name(name)
                .bio(bio)
                .photoUrl(photoUrl)
                .phoneNumber(phoneNumber)
                .website(website)
                .gender(Gender.findGenderByGivenKey(gender))
                .build();
    }
}