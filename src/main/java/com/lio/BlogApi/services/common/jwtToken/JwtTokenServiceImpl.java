package com.lio.BlogApi.services.common.jwtToken;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service("jwtTokenService")
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${app.jwt.issuer}")
    private String JWT_ISSUER;

    @Value("${app.jwt.privateKey}")
    private String PRIVATE_KEY;

    @Value("${app.jwt.sessionTime}")
    private Integer SESSION_TIME;

    @Override
    public String generateToken(Map<String, String> dataMap) {
        Algorithm algo = this.getAlgorithm();
        try {
            return JWT.create()
                    .withIssuer(this.JWT_ISSUER)
                    .withIssuedAt(new Date())
                    .withExpiresAt(createExpireDate())
                    .withPayload(dataMap)
                    .sign(algo);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Date createExpireDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, this.SESSION_TIME);
        return calendar.getTime();
    }

    @Override
    public String getTokenPayload(String token) {
        DecodedJWT decodedJWT = this.getDecodedToken(token);
        if (decodedJWT == null)
            return null;
        return decodedJWT.getPayload();
    }

    @Override
    public boolean isTokenExpire(String token) {
        JWTVerifier verifier = this.getVerifier();

        if (verifier == null)
            return true;

        Date expireDate = verifier.verify(token)
                .getExpiresAt();
        return expireDate.before(new Date());
    }

    @Override
    public boolean isValidToken(String token) {
        DecodedJWT decodedJWT = this.getDecodedToken(token);
        if (decodedJWT == null)
            return false;
        return !this.isTokenExpire(token) &&
                decodedJWT.getIssuer().equals(this.JWT_ISSUER);
    }

    private DecodedJWT getDecodedToken(String token) {
        JWTVerifier verifier = this.getVerifier();
        if (verifier == null)
            return null;
        return verifier.verify(token);
    }

    private JWTVerifier getVerifier() {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(this.JWT_ISSUER)
                    .build();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(this.PRIVATE_KEY.getBytes());
    }

}
