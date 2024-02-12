package br.com.erudio.cp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI getOpenApi() {
		return new OpenAPI().info(
			new Info()
			.title("REST API With Java 18")
			.version("v1")
			.description("Some description about your api")
			.termsOfService("https://www.erudio.com.br/blog/")
			.license(
				new License()
				.name("Apache 2")
				.url("https://www.erudio.com.br/blog/")
				)
			);
	}
	
}
