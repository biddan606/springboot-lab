package com.example.springbootlab.config.p6spy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class P6SpyConfig {

    @Bean
    public P6SpyFormatter p6SpyFormatter() {
        return new P6SpyFormatter();
    }
}
