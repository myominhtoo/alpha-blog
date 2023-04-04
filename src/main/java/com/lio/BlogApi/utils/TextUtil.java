package com.lio.BlogApi.utils;

import java.util.Map;

public class TextUtil {

    public static String bindString(String stringToBind, String stringForBind) {
        return String.format(stringToBind, stringForBind);
    }

    public static String bindString(String stringToBind, Map<String, String> mappings) {

        mappings.entrySet()
                .stream()
                .forEach(entry -> {
                    stringToBind.replace("[[" + entry.getKey() + "]]", entry.getValue());
                });

        return stringToBind;
    }

}
