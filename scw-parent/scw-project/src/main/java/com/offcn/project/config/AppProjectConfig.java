package com.offcn.project.config;

import com.offcn.utils.OssTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProjectConfig {
    @ConfigurationProperties(prefix = "oss") //把yml里得到oss属性值注入到OssTemplate对象里面
    @Bean
    public OssTemplate ossTemplate(){
        return new OssTemplate();
    }
}
