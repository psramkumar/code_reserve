package com.b2b.code.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@OpenAPIDefinition(
        info = @Info(title = "Phone Reservation System", version = "1.0",
                description = "PHONE",
                termsOfService = "https://www.company.com/about/legal/terms",
                contact = @Contact(name = "Support", email = "company@company.com", url = "https://company.com/contactus"),
                license = @License(name = "Company", url = "https://company.com/agreement")
        ),
        externalDocs = @ExternalDocumentation(description = "instructions for how to get access", url = "https://docs.company.com"),
        security = {@SecurityRequirement(name = "user_id"), @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
        }
)
@SecuritySchemes({
        @SecurityScheme(name = "user_id", type = SecuritySchemeType.APIKEY, paramName = "X-AUTH-USERID", in = SecuritySchemeIn.HEADER),
        @SecurityScheme(name = HttpHeaders.AUTHORIZATION, type = SecuritySchemeType.APIKEY, paramName = HttpHeaders.AUTHORIZATION, in = SecuritySchemeIn.HEADER)
})
@Schema(
        name = "Phone Reservation Management API",
        description = "APIs for Managing",
        externalDocs = @ExternalDocumentation(description = "For more information, see the link.", url = "https://docs.company.com/schema")
)
@Configuration
public class SwaggerConfig {
    /**
     * we are using SpringDoc to generate Open API 2.0 Standard so that we can port it in Developer portal as is.
     * to migrate the Swagger Spec to Open API 2.0 Standard please read the below Document.
     * https://springdoc.org/#migrating-from-springfox
     */
}