package com.lio.BlogApi.utils;

import java.util.Map;
import java.util.Map.Entry;

public class TextUtil {

    public static String bindString(String stringToBind, String stringForBind) {
        return String.format(stringToBind, stringForBind);
    }

    public static String bindString(String stringToBind, Map<String, String> mappings) {
        String result = stringToBind;

        for (Entry<String, String> entry : mappings.entrySet()) {
            result = result.replace("[[" + entry.getKey() + "]]", entry.getValue());
        }

        return result;
    }

}
