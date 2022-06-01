package com.instagram.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordRequestDto {
    String password;

    @JsonCreator
    public PasswordRequestDto(
            @NotBlank(message = "Password can not be empty or null")
            @Size(min = 6, max = 32, message = "Password length should be greater than 6 and less than 32") String password) {
        this.password = password;
    }
}
