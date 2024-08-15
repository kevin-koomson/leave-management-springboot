package com.kevo.LeavesRemaster.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevo.LeavesRemaster.modules.user.User;
import com.kevo.LeavesRemaster.modules.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtTokenVerifier {
    private final ObjectMapper jacksonObjectMapper;
    private final UserService userService;
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    @Value( value = "classpath:certificate/jwt-public-key.pem" )
    private RSAPublicKey publicKey;

    public String verifyToken(String tokenString) {
        try {
            return decodeToken(
                    JWT.require(Algorithm.RSA256(publicKey))
                            .build()
                            .verify(tokenString.replace("Bearer ", ""))
                            .getPayload()
            );
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid token");
        } catch (Exception e) {
            throw new JWTVerificationException(e.getMessage());
        }
    }
    public User getUserFromToken(String token) throws JsonProcessingException {
        Long userId = jacksonObjectMapper.readTree(verifyToken(token)).get("user_id").asLong();
        // decode payload to json
        return userService.getUserByUserId(userId);
    }

    public String decodeToken(String token) {
        String[] payload = token.split("\\.");
        return new String(decoder.decode(payload[0]));
    }

}
