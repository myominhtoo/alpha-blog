package com.lio.BlogApi.models.enums;

public enum SecretWord {

    CODE("oced"),
    EMAIL("meial"),
    JWT_CLAIM("adat"),
    TRUE("0001"),
    FALSE("0000");

    private String code;

    private SecretWord(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

}
