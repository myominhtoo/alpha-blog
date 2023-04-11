package com.lio.BlogApi.services.common.jwtToken;

import java.util.Date;
import java.util.Map;

public interface JwtTokenService {
    /*
     * for generating token
     */
    String generateToken(Map<String, String> dataMap);

    /*
     * for checking token is expire or not
     */
    boolean isTokenExpire(String token);

    /*
     * for checking token is valid or note
     * if token is expire or is not our token will be invalie
     */
    boolean isValidToken(String token);

    /*
     * getting expire date of token for generating
     */
    Date createExpireDate();

    /*
     * getting payload from token
     * will be null if token is not encoded safe
     */
    String getTokenPayload(String token);
}
