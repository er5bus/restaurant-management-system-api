package com.er5bus.restaurant.api.rest.docs;

import com.google.common.base.Predicates;

import java.util.List;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityReference;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@ComponentScan("com.er5bus.restaurant.api.rest")
public class SwaggerConfig {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

  private ApiKey apiKey() {
    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
      .securityReferences(defaultAuth())
      .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
      .build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .securityContexts(Arrays.asList(securityContext()))
      .securitySchemes(Arrays.asList(apiKey()))
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(Predicates.not(PathSelectors.regex("/error")))
      .build()
      .apiInfo(apiInfo());
  }


  private ApiInfo apiInfo() {
    String description = "\nUsername: test\nPassword: password\nInstractions: \n1-authenticate with Username And password\n2-copy the token You need to add `Bearer` before The TOKEN example `Bearer jwt_token`\n3-enjoy";
    return new ApiInfoBuilder()
      .title("REST API `Created By SFARI Rami`")
      .description(description)
      .termsOfServiceUrl("github")
      .licenseUrl("")
      .version("1.0")
      .build();
  }

}
