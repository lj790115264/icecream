package com.yl;

import com.yl.service.DevUrlTableService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public DevUrlTableService devUrlTableService() {
        return new DevUrlTableDbImpl();
    }
}

