package com.itis.service.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Multimap;
import com.itis.service.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Operation;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiListingScanningContext;
import springfox.documentation.spring.web.scanners.ApiModelReader;

import java.util.*;

@Component
public class LoginOperations extends ApiListingScanner {

    @Autowired
    private TypeResolver typeResolver;

    @Autowired
    public LoginOperations(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
        super(apiDescriptionReader, apiModelReader, pluginsManager);
    }

    @Override
    public Multimap<String, ApiListing> scan(ApiListingScanningContext context) {
        final Multimap<String, ApiListing> def = super.scan(context);

        final List<ApiDescription> apis = new LinkedList<>();

        final List<Operation> operations = new ArrayList<>();
        operations.add(new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.POST)
                .uniqueId("login")
                .parameters(Collections.singletonList(new ParameterBuilder()
                        .name("body")
                        .required(true)
                        .description("The body of request")
                        .parameterType("body")
                        .type(typeResolver.resolve(LoginDto.class))
                        .modelRef(new ModelRef("LoginDto"))
                        .build()))
                .summary("Log in student with created password and email from kpfu.ru")
                .tags(Collections.singleton("user-controller"))
                .notes("Log in as student on mobile client")
                .build());
        apis.add(new ApiDescription("user-controller", "/users/login", "Authorization on ITIS Service as student", operations, false));

        def.put("authentication", new ApiListingBuilder(context.getDocumentationContext().getApiDescriptionOrdering())
            .apis(apis)
            .description("Custom authentication")
            .build());

        return def;
    }
}
