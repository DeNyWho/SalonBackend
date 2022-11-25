package com.example.salonbackend.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun publicApiV1(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("anifox-api")
            .pathsToMatch("/api2/**")
            .build()
    }

    @Bean
    fun aniFoxOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info().title("Arina Backend")
                    .contact(
                        Contact().email("denis.akhunov123@gmail.com").name("Akhunov Denis")
                    )
                    .description("Arina Application API")
                    .version("v2")
                    .license(License().name("GPL 2.0").url("#"))
            )
    }

}