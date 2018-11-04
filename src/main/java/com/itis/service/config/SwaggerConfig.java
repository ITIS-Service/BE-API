package com.itis.service.config;

import com.fasterxml.classmate.TypeResolver;
import com.itis.service.dto.LoginDto;
import com.itis.service.swagger.LoginOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiModelReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Errors.class)
                .additionalModels(typeResolver.resolve(LoginDto.class))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.itis.service.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    @Primary
    @Bean
    public ApiListingScanner addExtraOperations(ApiDescriptionReader apiDescriptionReader,
                                                ApiModelReader apiModelReader,
                                                DocumentationPluginsManager pluginsManager) {
        return new LoginOperations(apiDescriptionReader, apiModelReader, pluginsManager);
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("ITIS Course Service API")
                .description("REST API of ITIS Service for mobile and web")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Timur Shafigullin", "https://vk.com/timbaev159", "hometim55@gmail.com"))
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
