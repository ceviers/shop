package com.cevier.shop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域请求配置
 */
@Configuration
public class CrosConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 设置cors配置
        CorsConfiguration config = new CorsConfiguration();
        // 允许访问的地址
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://server1:8080");
        // 设置是否发送cookies
        config.setAllowCredentials(true);
        // 设置允许的请求
        config.addAllowedMethod("*");
        // 设置允许的header
        config.addAllowedHeader("*");

        // 2. 添加路径映射
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
