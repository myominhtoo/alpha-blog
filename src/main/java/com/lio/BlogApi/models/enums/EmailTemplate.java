package com.lio.BlogApi.models.enums;

public enum EmailTemplate {

    EMAIL_VERIFICATION("mail-verification.txt");

    private String name;

    private EmailTemplate(String name) {
        this.name = name;
    }

    public String value() {
        return this.name;
    }

}
