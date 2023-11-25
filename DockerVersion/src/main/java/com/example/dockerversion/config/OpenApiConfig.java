package com.example.dockerversion.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Şafak Tamses",
                email = "safak.tamses@gmail.com",
                url = ""
        ),
        description = "rabam Java developer mülakatı için hazırlanmış uygulama. Proje gereksinimlerine github üzerinden ulaşabilirsiniz.",
        title = "Soru 5",
        version = "1.0",
        license = @License(
                name = "Licence name",
                url = "https://some-url.com"
        ),
        termsOfService = "Terms of service"
),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://localhost:8080"
                )
        }
)
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT auth description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class OpenApiConfig {
}
