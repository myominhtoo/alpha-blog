package com.lio.BlogApi.models.enums;

public enum ViewId {

    ACCOUNT(20),
    BLOG(30),
    CATEGORY(10),
    CODE(8),
    ACC_HISTORY(6),
    SUBSCRIBER(7);

    private Integer value;

    private ViewId(Integer value) {
        this.value = value;
    }

    public Integer bound() {
        return this.value;
    }

}
