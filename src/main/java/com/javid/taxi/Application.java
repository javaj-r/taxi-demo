package com.javid.taxi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.javid.taxi.Application.SECURITY_NAME;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Taxi API", version = "1.0", description = "Taxi API Information"))
@SecurityScheme(name = SECURITY_NAME, scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class Application {
    public static final String SECURITY_NAME = "Taxi API application security";

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
