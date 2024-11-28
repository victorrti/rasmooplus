package com.client.ws.rasmooplus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Value("${keycloak.auth.server-uri}")
    private String keycloakUri;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize ->{

            authorize.requestMatchers( AUTH_SWAGGER_LIST);
            authorize.requestMatchers(HttpMethod.POST, "/auth").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/auth/refresh-token").hasAnyAuthority("CLIENTE_READ_WRITE","ADMIN_READ","ADMIN_WRITE");
            authorize.requestMatchers(HttpMethod.GET,"/subscription-type").hasAnyAuthority("CLIENTE_READ_WRITE","ADMIN_READ","ADMIN_WRITE");
            authorize.requestMatchers("/auth/*").hasAnyAuthority("CLIENT_READ_WRITE");
            authorize.requestMatchers(HttpMethod.POST,"/payment/process").hasAnyAuthority("CLIENTE_READ_WRITE","ADMIN_READ","ADMIN_WRITE");
            authorize.requestMatchers(HttpMethod.POST,"/user/**").hasAnyAuthority("USER_READ","USER_WRITE","ADMIN_READ","ADMIN_WRITE");
            authorize.anyRequest().hasAnyAuthority("ADMIN_READ","ADMIN_WRITE");

        })
        .oauth2ResourceServer(auth2 -> auth2.jwt(jwt -> jwt.decoder(jwtDecoder())))
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();

    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withJwkSetUri(keycloakUri+"/protocol/openid-connect/certs").build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        Converter<Jwt, Collection<GrantedAuthority>> jwtCollectionConverter = jwt ->{
            Map<String, Object> resourceAcess = jwt.getClaim("real_access");
            Collection<String> roles = (Collection<String>) resourceAcess.get("roles");
            return roles.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtCollectionConverter);
        return jwtAuthenticationConverter;
    }
}
