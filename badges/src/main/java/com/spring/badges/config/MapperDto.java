package com.spring.badges.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperDto {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
