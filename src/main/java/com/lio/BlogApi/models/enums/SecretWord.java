package com.lio.BlogApi.models.enums;

public enum SecretWord {

    CODE("oced"),
    EMAIL("meial");

    private String code;

    private SecretWord(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

}
