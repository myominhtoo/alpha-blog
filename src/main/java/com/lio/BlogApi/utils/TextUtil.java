package com.lio.BlogApi.utils;

public class TextUtil {

    public static String bindString(String stringToBind, String stringForBind) {
        return String.format(stringToBind, stringForBind);
    }

}
