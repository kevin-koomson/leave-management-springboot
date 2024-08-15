package com.kevo.LeavesRemaster.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig {
    
    @Value( "${allowed.cors.url}" )
    private String allowedOrigin;
    
    /**
     * CORS configuration for external urls that are allowed.
     *
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer () {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings (@NotNull CorsRegistry registry) {
                registry.addMapping( "/**" )
                        .allowedOrigins(
                                allowedOrigin,
                                "http://localhost:9418",
                                "https://studio.apollographql.com"
                        )
                        .allowedMethods( "GET", "POST", "PUT", "DELETE", "OPTIONS" )
                        .allowedHeaders( "*" )
                        .allowCredentials( true );
            }
        };
    }
}