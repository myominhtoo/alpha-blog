package com.lio.BlogApi.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import com.lio.BlogApi.models.dtos.custom.AppUserDetails;
import com.lio.BlogApi.models.enums.Role;

public class HeaderUtil {

    public static HttpHeaders getHeadersFromMap(Map<String, String> map) {
        HttpHeaders headers = new HttpHeaders();

        map.entrySet()
                .forEach(entry -> {
                    headers.add(entry.getKey(), entry.getValue());
                });
        return headers;
    }

    public static Map<String, String> getMapFromPrincipal(AppUserDetails appUserDetails) {
        Map<String, String> map = new HashMap<>();
        map.put("username", appUserDetails.getAccount().getUsername());
        map.put("email", appUserDetails.getAccount().getEmail());
        map.put("role", Role.USER.value());
        return map;
    }

    public static Map<String, String> getAuthenticationHeader(AppUserDetails appUserDetails) {
        Map<String, String> map = new HashMap<>();
        map.put(HttpHeaders.AUTHORIZATION, appUserDetails.getToken());
        return map;
    }

}
