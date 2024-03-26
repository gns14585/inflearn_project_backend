package com.example.react_project_backend.config;

import com.example.react_project_backend.controller.formatter.LocalDateFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter());
    }

    // ------------------------ CORS 설정 ------------------------
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 어떤 경로에다가 CORS를 적용할지 "**" -> 전체경로
                .maxAge(500) // 빨리 연결이 안됐을경우
                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS") // 어떤 방식에 호출을 허용할건지
                .allowedOrigins("*"); // 어디에서부터 들어오는 연결을 허락할지 설정 "*" -> 모든곳
    }
}
