package com.product.globie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SercutiryConfig {
    @Value("${api.version}")
    private String apiVersion;

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        String[] publicEndpoints = new String[] {
                apiVersion + "/authen/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                apiVersion + "/product/**",
                apiVersion + "/account/**",
                apiVersion + "/product_category/**",
                apiVersion + "/post/**",
                apiVersion + "/report/**",
                apiVersion + "/bookmark/**",
                apiVersion + "/comment/**",
                apiVersion + "/post_category/**"


        };

//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.POST, publicEndpoints).permitAll()
//                        .requestMatchers(HttpMethod.GET, publicEndpoints).permitAll()
//                        .requestMatchers(HttpMethod.PUT, publicEndpoints).permitAll()
//                        .requestMatchers(HttpMethod.DELETE, publicEndpoints).permitAll()
//                        .requestMatchers("/auth/google").permitAll() // Allow access to root path
//                        .anyRequest().authenticated() // Any other request requires authentication
//                )
//                .oauth2Login(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .oauth2ResourceServer(oauth2 ->
//                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                );

        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.POST, publicEndpoints).permitAll()
                .requestMatchers(HttpMethod.GET, publicEndpoints).permitAll()
                .requestMatchers(HttpMethod.PUT, publicEndpoints).permitAll()
                .requestMatchers(HttpMethod.DELETE, publicEndpoints).permitAll()
                .anyRequest()
                .authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
