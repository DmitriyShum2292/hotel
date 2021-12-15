package com.example.hotels.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut("@annotation(Loggable)")
    public void pointCut(){
        //point cut method just for annotation
    }
    @Around("pointCut()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper mapper= new ObjectMapper();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] array = joinPoint.getArgs();
        logger.info("");
        logger.info("*******"+className+": "+methodName+"*********"+"arguments: "
                +mapper.writeValueAsString(array));
        Object object = joinPoint.proceed();
        logger.info("*******"+className+": "+methodName+"*********"+"Response: "
                +mapper.writeValueAsString(object));
        logger.info("");
        return object;
    }
}

