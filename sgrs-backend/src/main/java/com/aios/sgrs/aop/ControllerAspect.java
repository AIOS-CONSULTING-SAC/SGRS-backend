package com.aios.sgrs.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);


    @Around("execution(* com.aios.sgrs.controller..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Inicia llamada a método: {} en el controlador: {} con parametros: {}",
                methodName, className, joinPoint.getArgs());
        Object result = joinPoint.proceed();
        logger.info("Fin llamada a método: {} en el controlador: {}", methodName, className);
        return result;
    }
}