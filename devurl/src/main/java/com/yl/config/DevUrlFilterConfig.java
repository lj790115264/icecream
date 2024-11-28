package com.yl.config;

import com.yl.DevUrlFeignFilter;
import com.yl.DevUrlFilter;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevUrlFilterConfig {

    @Bean
    public RequestInterceptor DevUrlFeignFilter() {
        return new DevUrlFeignFilter();
    }

    @Bean
    public DevUrlFilter devUrlFilter() {

        return new DevUrlFilter();
    }

}

