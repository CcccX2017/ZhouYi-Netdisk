package cn.codex.netdisk.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.TimeZone;

/**
 * 通用配置
 *
 * @author codex
 * @since 2021-02-12
 */
@Configuration
public class GeneralConfig {
    
    /**
     * 跨域
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        // 设置访问源地址
//        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    @Bean
    public ObjectMapper jacksonObjectMapperCustomization(){
        return new Jackson2ObjectMapperBuilder()
                .timeZone(TimeZone.getDefault())
                // 解决long类型传递到前端的精度问题
                .serializerByType(Long.class, ToStringSerializer.instance)
                .build();
    }
}
