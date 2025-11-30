package com.dogimax.dogimaxapi.shared.infrastructure.documentation.openapi.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Value("${spring.application.name}")
    String applicationName;
    @Value("${documentation.application.description}")
    String applicationDescription;
    @Value("${documentation.application.version}")
    String applicationVersion;
    @Value("${server.url:http://localhost:8080}")
    String serverUrl;
    
    @Bean
    public OpenAPI learningPlatformOpenApi() {
        var openApi = new OpenAPI();
        openApi.info(new Info()
                .title(this.applicationName)
                .description(this.applicationDescription)
                .version(this.applicationVersion)
                .license(new License().name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("ACME Learning Platform wiki Documentation")
                        .url("https://acme-learning-platform.wiki.github.io/docs"));

        // Add server configuration
        List<Server> servers = new ArrayList<>();
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription("API Server");
        servers.add(server);
        openApi.servers(servers);

        // Add a security scheme

        final String securitySchemeName = "bearerAuth";

        openApi.addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));



        return openApi;
    }

}
