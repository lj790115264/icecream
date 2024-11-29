package com.yl;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class DevUrlFeignFilter implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header(DevUrlFilter.DEV_URL_TRACE_ID_HEADER, DevUrlFilter.urlThreadLocal.get());
        requestTemplate.header(DevUrlFilter.DEV_URL_TRACE_ID_CROSS_HEADER, JsonUtil.toJson(DevUrlFilter.urlCrossThreadLocal.get()));
    }
}
