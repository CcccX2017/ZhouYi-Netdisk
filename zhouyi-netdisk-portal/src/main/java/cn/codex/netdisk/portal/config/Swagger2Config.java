package cn.codex.netdisk.portal.config;

import cn.codex.netdisk.common.config.PortalConfig;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 *
 * @author codex
 * @since 2021-01-30
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {

    @Autowired
    private PortalConfig portalConfig;

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
                .build();
                /*.securityContexts(securityContexts())
                .securitySchemes(securitySchemes());*/
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("舟意网盘接口文档 - 门户网站")
                .description("舟意网盘门户网站的接口文档")
                .contact(new Contact(portalConfig.getAuthor(), "http://localhost:9001/doc.html", null))
                .version(portalConfig.getVersion())
                .build();
    }
}
