package com.lio.BlogApi.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

public class ErrorMapUtil {

    public static Map<String, String> getErrorMapFromBindingResult(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors()
                .stream()
                .forEach(fieldError -> {
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                });
        return map;
    }

    public static Map<String, String> getErrorMapFromValue(String error) {
        Map<String, String> map = new HashMap<>();
        map.put("error", error);
        return map;
    }

}
