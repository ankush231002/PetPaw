package com.cat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps http://localhost:8080/uploads/file.jpg 
        // to the actual "uploads" folder on your disk
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}