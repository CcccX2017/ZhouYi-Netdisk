package cn.codex.netdisk.portal.config.security;

import cn.codex.netdisk.portal.config.security.component.JwtInterceptor;
import cn.codex.netdisk.portal.config.security.component.RestUrlInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author codex
 * @since 2021-02-09
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    RestUrlInterceptor restUrlInterceptor() {
        return new RestUrlInterceptor();
    }

    /**
     * 设置拦截路径
     *
     * @param registry 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/portal/**")
                .excludePathPatterns("/portal/user/login", "/portal/user/logout", "/captcha");
        registry.addInterceptor(restUrlInterceptor()).addPathPatterns("/backend/**");
    }
}
