package com.ducle.user_service.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class ServiceConfiguration {
    @Bean
    public UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder.newInstance();
    }
}
