package com.jithesh.example;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  @ConditionalOnMissingBean
  public Docket todoApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select().apis(RequestHandlerSelectors.basePackage("com.SampleApp.challenge.java.controller"))
        .paths(regex("/.*"))
        .build()
        .groupName(Docket.DEFAULT_GROUP_NAME)
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(true)
        .forCodeGeneration(true);
  }

  public ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Todo API")
        .description("The service enables consumers to create, update, delete and list their todo items.")
        .contact(new Contact("Jithesh Kozhipurath", "", ""))
        .version("1.0.0")
        .build();
  }
}
