package com.lio.BlogApi.models.enums;

public enum Prefix {

    USER("usr"),
    ADMIN("adm"),
    BLOG("blg"),
    CODE("CD"),
    ACC_HISTORY("achs"),
    SUBSCRIBER("sub"),
    CATEGORY("cate"),
    CATEGORY_IMAGE("IMAGE_CATEGORY"),
    BLOG_IMAGE("IMAGE_BLOG");

    private String value;

    private Prefix(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
