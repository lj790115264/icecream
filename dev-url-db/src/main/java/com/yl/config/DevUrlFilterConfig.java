package com.yl.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yl.DevUrlFeignFilter;
import com.yl.DevUrlFilter;

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

