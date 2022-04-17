package com.cos.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        //서버가 응답할 때 JSON을 자바스크립트에서 처리할 수 있게 할지를 설정함
        //Access-Control-Allow-Credentials: true 로 응답하지 않으면, 응답은 무시되고 웹 컨텐츠는 제공되지 않는다.
        config.setAllowCredentials(true);

        //모든 IP에 응답을 허용
        config.addAllowedOrigin("*");

        //모든 헤더에 응답을 허용
        config.addAllowedHeader("*");

        //모든 GET, POST, PUT, DELETE 요청에 응답을 허용
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }

}
