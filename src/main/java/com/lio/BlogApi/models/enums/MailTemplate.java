package com.lio.BlogApi.models.enums;

public enum MailTemplate {

    EMAIL_VERIFICATION("mail-verification.txt");

    private String name;

    private MailTemplate(String name) {
        this.name = name;
    }

    public String value() {
        return this.name;
    }

}
