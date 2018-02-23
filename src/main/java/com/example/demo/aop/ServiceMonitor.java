package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {
    
    private static final Logger LOG = LoggerFactory.getLogger(ServiceMonitor.class);

    @Before("execution(* com.example.demo..*Controller.*(..))")
    public void logServiceBefore(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder(joinPoint.toLongString());
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            sb.append(String.format(" [%d]=%s", i, joinPoint.getArgs()[i]));
        }
        LOG.info("@Before {}", sb.toString());
    }
    
    @After("execution(* com.example.demo..*Controller.*(..))")
    public void logServiceAfter(JoinPoint joinPoint) {
        LOG.info("@After {}", joinPoint.toLongString());
    }

    @AfterReturning("execution(* com.example.demo..*Controller.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        LOG.info("@AfterReturning {}", joinPoint.toLongString());
    }
    
    @AfterThrowing("execution(* com.example.demo..*Controller.*(..))")
    public void logServiceError(JoinPoint joinPoint) {
        LOG.info("@AfterThrowing {}", joinPoint.toLongString());
    }
    
    @Before("execution(* org.springframework.jdbc.core.JdbcTemplate..*(..))")
    public void printSql(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder(joinPoint.toLongString());
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            sb.append(String.format(" [%d]=%s", i, joinPoint.getArgs()[i]));
        }
        LOG.info("SQL {}", sb.toString());
    }
}
