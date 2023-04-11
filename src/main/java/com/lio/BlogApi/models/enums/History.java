package com.lio.BlogApi.models.enums;

public enum History {

    ACCOUNT_VERIFIED("Account was successfully verified!");

    private String value;

    private History(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
