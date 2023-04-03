package com.lio.BlogApi.models.enums;

public enum AccountStatus {

    NOT_YET_VERIFIED("Account was not yet verified!"),
    VERIFED("Account had verified!"),
    LOCKED("Account was temporarily locked!");

    private String status;

    private AccountStatus(String status) {
        this.status = status;
    }

    public String value() {
        return this.status;
    }

}
