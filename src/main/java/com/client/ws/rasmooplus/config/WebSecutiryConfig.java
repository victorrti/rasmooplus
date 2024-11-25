package com.client.ws.rasmooplus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecutiryConfig   {



    private static final String[] AUTH_SWAGGER_LIST = {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize ->{
            authorize.anyRequest().authenticated();
            authorize.requestMatchers( AUTH_SWAGGER_LIST);
            authorize.requestMatchers(HttpMethod.GET, "/subscription-type/*");
            authorize.requestMatchers(HttpMethod.POST, "/user");
            authorize.requestMatchers(HttpMethod.POST, "/payment/process");
            authorize.requestMatchers(HttpMethod.POST, "/auth");
            authorize.requestMatchers( "/auth/recovery-code/*");
        })

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }
}
