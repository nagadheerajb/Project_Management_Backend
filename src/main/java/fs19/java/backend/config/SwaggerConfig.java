package fs19.java.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Management App API Docs")
                        .version("0.1")
                        .description("Below Docs helps to understand the Project Management App Documentation")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .components(new Components()
                        .addParameters("page", pageableParameter("page", "Page number (0-based index)", "0", "integer"))
                        .addParameters("size", pageableParameter("size", "Number of items per page", "10", "integer"))
                        .addParameters("sort", pageableParameter("sort", "Sorting field and direction, e.g., 'createdDate,DESC'", "createdDate,DESC", "string"))
                        .addResponses("BadRequest", createResponse("400", "Invalid input data"))
                        .addResponses("NotFound", createResponse("404", "Resource not found"))
                        .addResponses("InternalError", createResponse("500", "Internal server error"))
                        .addResponses("Created", createResponse("201", "Resource created successfully"))
                        .addResponses("NoContent", createResponse("204", "No content")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .addSecurityItem(new SecurityRequirement().addList("workspaceId"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Authorization header using the Bearer scheme"))
                        .addSecuritySchemes("workspaceId",
                                new SecurityScheme()
                                        .name("workspaceId")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("Workspace Identifier")));
    }

    /**
     * Creates a reusable query parameter for pageable.
     *
     * @param name        Name of the query parameter
     * @param description Description of the parameter
     * @param exampleValue Example value of the parameter
     * @param type        Type of the parameter (e.g., integer, string)
     * @return Configured Parameter object
     */
    private Parameter pageableParameter(String name, String description, String exampleValue, String type) {
        return new Parameter()
                .name(name)
                .in("query")
                .description(description)
                .required(false)
                .schema(new Schema<>().type(type).example(exampleValue));
    }

    private ApiResponse createResponse(String code, String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>().type("string").example(description))));
    }
}
