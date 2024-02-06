package br.com.erudio.cp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.erudio.cp.converter.YamlJacksonConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${cors.origenPatterns:default}")
	private String origenPatterns = "";
	
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJacksonConverter());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var alloweOrigens = origenPatterns.split(",");
		
		registry
			.addMapping("/**")
			//.allowedMethods("GET", "POST", "PUT") //Pode especificar os métodos
			.allowedMethods("*")
			.allowedOrigins(alloweOrigens)
			.allowCredentials(true); //Para permitir a autenticação
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		//Via queryParam: http://localhost:8080/person/v1?mediaType=xml
		/*configurer.favorParameter(true)
					.parameterName("mediaType").ignoreAcceptHeader(true)
					.useRegisteredExtensionsOnly(false)
					.defaultContentType(MediaType.APPLICATION_JSON)
					.mediaType("json", MediaType.APPLICATION_JSON)
					.mediaType("xml", MediaType.APPLICATION_XML);*/
		
		//Via headerParam: http://localhost:8080/person/v1
		//Header : Accept - application/xml
		configurer.favorParameter(false)
			.ignoreAcceptHeader(false)
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yaml", MediaType.parseMediaType("application/x-yaml"));
					
	}

	
}
