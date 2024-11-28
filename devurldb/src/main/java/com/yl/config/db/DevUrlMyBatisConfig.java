package com.yl.config.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.yl.mapper"})
public class DevUrlMyBatisConfig {

    @Bean
    public DevUrlInterceptor tableNameInterceptor() {

        return new DevUrlInterceptor();
    }
}

