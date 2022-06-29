package com.wq.dora.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/**
 * controller日志切面
 * @author wuqian
 * @date 2022/6/28
 */
@Aspect
@Slf4j
@Component
public class ControllerLogAspect {

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    @Pointcut("execution(* com.wq.dora.controller..*.*(..))")
    public void controllers() {
    }

    @Before("controllers()")
    public void deBefore(final JoinPoint joinPoint) {

        // 接收到请求，记录请求内容
        log.debug("================  Controller Log Start  ===================");
        this.startTime = LocalDateTime.now();
        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            final HttpServletRequest request = attributes.getRequest();
            log.debug("==> Request: [{}]{}", request.getMethod(), request.getRequestURL());
        }
        log.debug(
                "==>  Method: {}",
                joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName());
        log.debug("==>    Args: {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 后置结果返回
     *
     * @param result 结果
     */
    @AfterReturning(pointcut = "controllers()", returning = "result")
    public void doAfterReturning(final Object result) {
        // 处理请求的时间差
        final long difference = ChronoUnit.MILLIS.between(this.startTime, LocalDateTime.now());
        log.debug("==>   Spend: {}s", difference / 1000.0);
        log.debug("==>  Return: {}", result);
        log.debug("================  Controller Log End  =====================");
    }


}
