package com.intentfilter.profileservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("platform")
public class PlatformConfig {

    private String domain;

    public String getPlatformDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
