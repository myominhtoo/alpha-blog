package com.lio.BlogApi.models.enums;

public enum Prefix {

    USER("usr"),
    ADMIN("adm"),
    BLOG("blg");

    private String value;

    private Prefix(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
