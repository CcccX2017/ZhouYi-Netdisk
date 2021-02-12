package cn.codex.netdisk.portal.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置类
 *
 * @author codex
 * @since 2021-01-30
 */
@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {
    
    @Autowired
    private PortalConfig portalConfig;
    
    private final OpenApiExtensionResolver openApiExtensionResolver;
    
    @Autowired
    public Swagger2Config(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }
    
    /**
     * 是否开启swagger
     */
    @Value("${swagger.enabled}")
    private boolean enabled;
    
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(enabled)
                .groupName("门户网站")
                // 用来创建该API的基本信息
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                // .apis(RequestHandlerSelectors.basePackage("cn.codex.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .extensions(openApiExtensionResolver.buildSettingExtensions());
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("舟意网盘接口文档 - 门户网站")
                .description("舟意网盘门户网站的接口文档")
                .contact(new Contact(portalConfig.getAuthor(), "http://localhost:9001/doc.html", null))
                .version(portalConfig.getVersion())
                .build();
    }
    
    private List<? extends SecurityScheme> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        apiKeys.add(apiKey);
        
        return apiKeys;
    }
    
    private List<SecurityContext> securityContexts() {
        // 设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath());
        return result;
    }
    
    private SecurityContext getContextByPath() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("^(?!/portal/user/login|/portal/user/register|/captcha).*$"))
                .forPaths(PathSelectors.regex("^/portal(?!/login|/register|/captcha).*$"))
                .build();
    }
    
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }
}
