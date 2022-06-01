package com.instagram.model;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    CUSTOM("custom"),
    PREFER_NOT_TO_SAY("prefer not to say");

    private final String key;

    Gender(String key) {
        this.key = key;
    }

    public static Gender findGenderByGivenKey(String key) {
        for (Gender gender : values()) {
            if (gender.key.equals(key)) {
                return gender;
            }
        }

        throw new IllegalArgumentException(String.format("No gender for given key: %s", key));
    }
}