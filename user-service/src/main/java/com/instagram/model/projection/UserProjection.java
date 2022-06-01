package com.instagram.model.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface UserProjection {
    String getName();
    String getUsername();
    String getPhotoUrl();
    String getBio();
    String getWebsite();
    String getEmail();
    String getPhoneNumber();
    String getRole();
    String getGender();
    Integer getFollowersNumber();
    Integer getFollowingsNumber();
}