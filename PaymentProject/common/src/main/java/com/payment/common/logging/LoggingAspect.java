
package com.payment.common.logging;

import jakarta.validation.constraints.NotNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.payment.common.logging.producer.LoggingProducer;

@Aspect
@Component
public class LoggingAspect {
    private final LoggingProducer loggingProducer;

    public LoggingAspect(LoggingProducer loggingProducer) {
        this.loggingProducer = loggingProducer;
    }

    /**
     * Web adapter 패키지 내 모든 메서드 실행 전 로깅
     */
    @Before("execution(* com.payment.*.adapter.in.web.*.*(..))")
    public void beforeMethodExecution(@NotNull JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String message = String.format("Before executing %s.%s(...)", className, methodName);
        loggingProducer.sendMessage("logging", message);
        // TODO : 위 하드 코딩 된 Key는 환경에서는 Enum이나 상수로 관리
        // Producing Access Log

    }
}