package com.kevo.LeavesRemaster.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthenticationConfig implements AuthenticationEntryPoint {
    
    /**
     * Begins the authentication process when an unauthenticated user tries to access a secured resource
     *
     * @param request     Requests to the server.
     * @param response    Response from the server.
     * @param authException Authentication exception.
     * @throws IOException Thrown if there is an error.
     */
    @Override
    public void commence (
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.sendError( HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage() );
    }
}
