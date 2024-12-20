package com.egg.libreria;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize.requestMatchers("/css/", "/js/", "/img/", "/**").permitAll())
                .csrf(csrt -> csrt.disable());
        return http.build();
    }
}
