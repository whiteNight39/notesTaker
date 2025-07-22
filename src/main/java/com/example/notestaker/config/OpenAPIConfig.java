package com.example.notestaker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fredrick 🖤 | Tesa Student Management API Documentation")
                        .description("Comprehensive documentation for Tesa's Java-based student management backend.")
                        .version("1.0.0"));
    }
}