package com.example.userservice.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Value("${app.client.id}")
	private String clientId;
	@Value("${app.client.secret}")
	private String clientSecret;

	@Value("${token.url}")
	private String accessTokenUri;

	@Bean
	public Docket userApi() {

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.example.userservice.controller"))
				.paths(PathSelectors.any()).build().securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Arrays.asList(securitySchema())).apiInfo(apiInfo());

	}

	

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = newArrayList();

		List<GrantType> grantTypes = newArrayList();
		GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
		grantTypes.add(passwordCredentialsGrant);

		return new OAuth("oauth2", authorizationScopeList, grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
		authorizationScopes[2] = new AuthorizationScope("write", "write all");

		return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
	}

	@SuppressWarnings("deprecation")
	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration(clientId, clientSecret, "", "", "Bearer access token", ApiKeyVehicle.HEADER,
				HttpHeaders.AUTHORIZATION, "");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User service").description("").license("Personal Learning").version("1.0.0")
				.build();
	}

}
