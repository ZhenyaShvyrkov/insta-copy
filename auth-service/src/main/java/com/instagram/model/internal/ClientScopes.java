package com.instagram.model.internal;

public enum ClientScopes {
    READ("read"),
    MODIFY("modify");

    private final String value;

    ClientScopes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}