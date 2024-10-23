package com.icecream.helplog.config;

import com.icecream.helplog.util.HelpLog;
import groovy.util.logging.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author andre.lan
 */
@Aspect
@Component
@Slf4j
public class HelpLogAop {

    @Pointcut("@annotation(com.xxl.job.core.handler.annotation.XxlJob)")
    public void logPointcut() {

    }

    @Around(value = "logPointcut()")
    public Object handler(ProceedingJoinPoint jp) throws Throwable {

        HelpLog.remove();
        Object response = null;
        try {

            response = jp.proceed();
        } catch (Exception var6) {
            HelpLog.info("系统异常", var6);
            throw var6;
        }  finally {
            HelpLog.remove();
        }

        return response;
    }

}
