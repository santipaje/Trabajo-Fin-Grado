package com.socialhub.tfg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@CrossOrigin(origins = "http://localhost:4200")
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/tfg/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .exposedHeaders("*");

                registry.addMapping("/tfg/login")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .exposedHeaders("*");

                registry.addMapping("/tfg/verify-email")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*");

                registry.addMapping("/tfg/register")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*");

                registry.addMapping("/tfg/authenticate-token")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*");

            }
        };
    }

}
