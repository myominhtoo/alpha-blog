package com.lio.BlogApi.models.enums;

public enum ViewId {

    ACCOUNT(20),
    BLOG(30),
    CODE(8),
    ACC_HISTORY(6),
    SUBSCRIBER(7),
    CATEGORY(6);

    private Integer value;

    private ViewId(Integer value) {
        this.value = value;
    }

    public Integer bound() {
        return this.value;
    }

}
