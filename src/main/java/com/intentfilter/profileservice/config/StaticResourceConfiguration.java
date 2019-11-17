package com.intentfilter.profileservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
    private FileStoreConfig fileStoreConfig;

    public StaticResourceConfiguration(FileStoreConfig fileStoreConfig) {
        this.fileStoreConfig = fileStoreConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + fileStoreConfig.getUploadDir());
    }
}