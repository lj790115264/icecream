package com.yl.config;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yl.DevUrlFilter;
import com.yl.JsonUtil;
import com.yl.service.DevUrlTableService;
import groovy.util.logging.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author andre.lan
 */
@Aspect
@Component
@Slf4j
public class DevUrlJobAop {

    @Pointcut("@annotation(com.xxl.job.core.handler.annotation.XxlJob)")
    public void logPointcut() {

    }

    @Autowired
    private DevUrlTableService service;

    @Around(value = "logPointcut()")
    public Object handler(ProceedingJoinPoint jp) throws Throwable {

        Object response;
        try {

            MethodSignature signature = (MethodSignature) jp.getSignature();
            Method method = signature.getMethod();
            XxlJob annotation = method.getAnnotation(XxlJob.class);
            String jobName = annotation.value();

            DevUrlFilter.urlThreadLocal.set(jobName);

            List<String> uriCrossList = new ArrayList<>();
            uriCrossList.add(jobName);
            DevUrlFilter.urlCrossThreadLocal.set(uriCrossList);

            DevUrlFilter.tableThreadLocal.set(new HashSet<>());

            response = jp.proceed();
        } finally {

            service.doing();
            DevUrlFilter.urlThreadLocal.remove();
            DevUrlFilter.urlCrossThreadLocal.remove();
            DevUrlFilter.tableThreadLocal.remove();
        }

        return response;
    }

}
