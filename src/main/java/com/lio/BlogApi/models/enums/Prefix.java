package com.lio.BlogApi.models.enums;

public enum Prefix {

    USER("usr"),
    ADMIN("adm"),
    BLOG("blg"),
    CODE("CD"),
    ACC_HISTORY("achs"),
    SUBSCRIBER("sub"),
    CATEGORY("cate");

    private String value;

    private Prefix(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
