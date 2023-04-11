package com.lio.BlogApi.models.enums;

public enum Role {
    ADMIN("001MDA"),
    USER("001RSU");

    private String value;

    private Role(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
