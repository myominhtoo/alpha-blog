package com.lio.BlogApi.models.enums;

public enum Message {

    INVALID_REQUEST_BODY("Invalid Request Body!"),
    INVALID("Invalid"),

    DUPLICATE_EMAIL("Your email has already been used by another account!"),
    ACCOUNT_LOCKED("Your account was temporarily locked!"),
    ACCOUNT_NEED_VERIFICATION("You need to verify your account!"),
    UNKNOWN_ERROR("Unknown error occured!"),

    REGISTER_SUCCESS("Successfully Registered,Please check your email to verify account!"),
    INVALID_REQUEST("Invalid Request,Please use valid information!"),
    UPLOAD_FAILED("Failed to upload file!"),
    UPLOAD_SUCCESS("Successfully Uploaded!"),

    ACCOUNT_VERIFY_SUCCESS("Successfully verified account!"),
    ACCOUNT_VERIFY_FAIL("Failed to verify your account!"),

    INVALID_ACCOUNT("Invalid account!"),
    LOGIN_SUCCESS("Successfully Logged In!"),

    HAD_SUBSCRIBED("Recommend to use unique email to get more informations about our site!"),
    SUBSCRIBE_SUCCESS("Successfully subscribed!"),
    UNSUBSCRIBE_SUCCESS("Successfully cancelled subscription!"),

    CREATE_CATEGORY_SUCCESS("Successfully added new category!"),
    UPDATE_CATEGORY_SUCCESS("Successfully updated category!"),
    DELETE_CATEGORY_SUCCESS("Successfully deleted category!"),
    UNDELETE_CATEGORY_SUCCESS("Successfully restored category!"),
    DUPLICATE_CATEGORY_NAME("Please use unique category name to be more informative!"),
    INVALID_FILE_FORMAT("Invalid file format!"),

    SUCCESS("Success!");

    private String value;

    private Message(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
