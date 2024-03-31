package com.eside.advertisment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    protected final static String devUrl = "http://localhost:8095";

    //private final static String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        //Server prodServer = new Server();
        //prodServer.setUrl(prodUrl);
        //prodServer.setDescription("Server URL in Production environment");

        //Contact contact = new Contact();
        //contact.setEmail("eside@gmail.com");
        //contact.setName("eside");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("ESIDE ACCOUNT API'S")
                .version("1.0")
                //.contact(contact)
                //.description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.bezkoder.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}