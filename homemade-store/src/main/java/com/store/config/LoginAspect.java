package com.store.config;

import com.store.domain.Audit;
import com.store.repository.AuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
class LoggingAspect {

    private final AuditRepository auditRepository;

    public LoggingAspect(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @AfterReturning("execution(* com.store.service.*.*(..))")
    public void log(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.getSignature().getName() +
                " from " + joinPoint.getTarget().getClass() +
                " will be executed. Timestamp: " + LocalDateTime.now());

        Audit audit = new Audit(joinPoint.getSignature().toString(), joinPoint.getTarget().getClass().getCanonicalName(), LocalDateTime.now());
        auditRepository.save(audit);
    }

}