package com.kevo.LeavesRemaster.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevo.LeavesRemaster.modules.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenVerifier jwtTokenVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")) {
            throw new AuthenticationException("Invalid token");
        }

        token = token.substring(7);
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws JsonProcessingException {
        if(token.isBlank() || token.isEmpty()) return null;
        User user = jwtTokenVerifier.getUserFromToken(token);
        if (Objects.isNull(user)) return null;
        List<SimpleGrantedAuthority> permissions = user.getAccessLevel().getPermissions()
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(
                user, null, permissions);
    }
}
