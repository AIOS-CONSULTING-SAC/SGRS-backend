package com.aios.sgrs.aop;

import com.aios.common.exception.AccesoDaoException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DaoExceptionAspect {
    private static final Logger logger = LoggerFactory.getLogger(DaoExceptionAspect.class);

    @Around("execution(* com.aios.sgrs.dao..*(..))")
    public Object handleDaoExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DataAccessException e) {
            String daoName = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.error("Excepción SQL en {}.{} - {}", daoName, methodName, e.getMessage(), e);
            throw new AccesoDaoException(daoName, methodName, e.getMessage(), e);
        } catch (Exception e) {
            String daoName = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.error("Excepción en dao: {}.{} - {}", daoName, methodName, e.getMessage(), e);
            throw e;
        }
    }
}
