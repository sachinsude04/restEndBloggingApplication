package com.blogapp.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys() {
		
		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
	}
	
	private List<SecurityContext> securityContexts()
	{
		return Arrays.asList(SecurityContext.builder().securityReferences(null).build());
	}
	
	private List<SecurityReference> securityReferences()
	{
		AuthorizationScope scopes= new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scopes}));
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
	public ApiInfo getApiInfo()
	{
		return new ApiInfo("Blogging BackEnd application", "This project is developed by sachin sude", "1.0", "Terms of service", new Contact("Sachin", "GitHub:sachinsude04", "sudesachin040695@gmail.com"), "License of Api", "Api license Url",Collections.emptyList());
	}
}
