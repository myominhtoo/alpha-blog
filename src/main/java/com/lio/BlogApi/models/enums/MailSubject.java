package com.lio.BlogApi.models.enums;

public enum MailSubject {

    EMAIL_VERIFICATION("Sending email verification from Alpha Blog App!");

    private String msg;

    private MailSubject(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }

}
