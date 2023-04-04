package com.lio.BlogApi.models.enums;

public enum CodeStatus {

    NOT_VERIFIED("Had not been verified yet!"),
    VERIFIED("Had Verified!"),
    RESET("This code has been resetted!");

    private String status;

    private CodeStatus(String status) {
        this.status = status;
    }

    public String status() {
        return this.status;
    }

}
