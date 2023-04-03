package com.lio.BlogApi.models.enums;

public enum Message {

    INVALID_REQUEST_BODY("Invalid Request Body!"),
    INVALID("Invalid"),
    DUPLICATE_EMAIL("Your email has already been used by another account!"),
    ACCOUNT_LOCKED("Your account was temporarily locked!"),
    ACCOUNT_NEED_VERIFICATION("You need to verify your account!"),
    UNKNOWN_ERROR("Unknown error occured!"),
    REGISTER_SUCCESS("Successfully Registered,Please check your email to verify account!");

    private String value;

    private Message(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
