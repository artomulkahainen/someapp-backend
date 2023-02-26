package com.someapp.backend.utils.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    static {
        // use Reusable Enums for Swagger generation:
        // see https://springdoc.org/#how-can-i-apply-enumasref-true-to-all-enums
        io.swagger.v3.core.jackson.ModelResolver.enumsAsRef = true;
    }

    // ... you can also describe your api bellow
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("GimmeVibe API")
                                .description("For using awesome someapp")
                                .version("v0.0.1")
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("")
                                .url("")
                );
    }
}

