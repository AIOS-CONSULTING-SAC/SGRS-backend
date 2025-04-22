package com.aios.sgrs.aop;

import com.aios.common.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionAspect.class);

    @Around("execution(* com.aios.sgrs.service..*(..))")
    public Object handleServiceExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (ServiceException e) {
            String serviceName = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.error("Excepción en servicio: {}.{} - {}", serviceName, methodName, e.getMessage(), e);
            throw new ServiceException(serviceName, methodName, e.getMessage(), e);
        }
    }
}