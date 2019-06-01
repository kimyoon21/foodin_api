package app.foodin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {

        return Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(params)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo())
                .securityContexts(mutableListOf(securityContext()))
                .securitySchemes(mutableListOf(apiKey()))
    }

    private fun apiKey(): ApiKey {
        return ApiKey(
                "apiKey", // name: My key - Authorization
                "Authorization", // keyname: api_key
                "header")
    }

    private fun apiEndPointsInfo(): ApiInfo {

        return ApiInfoBuilder().title("foodin REST API")
                .description(" REST API")
                .contact(Contact("Yoon", "foodin.app", "foodin@foodin.app"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build()
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope(
                "global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("apiKey",
                authorizationScopes))
    }
}
