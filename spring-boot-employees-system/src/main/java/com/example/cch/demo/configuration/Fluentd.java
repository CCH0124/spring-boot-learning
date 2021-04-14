package com.example.cch.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "cch.fluentd")
@Setter
@Getter
public class Fluentd {
    @Value("${cch.fluentd.host}")
    private String host;
    @Value("${cch.fluentd.port}")
    private String port;
}
