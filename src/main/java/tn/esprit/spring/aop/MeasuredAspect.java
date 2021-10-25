package tn.esprit.spring.aop;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;



@Aspect
@Configuration
@EnableAspectJAutoProxy
public class MeasuredAspect {
	
	private static final Logger log = LogManager.getLogger(MeasuredAspect.class);
	
    @Around("@annotation(tn.esprit.spring.aop.Measured)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Measured measured = method.getAnnotation(Measured.class);
        String message = measured.message();
        String methodName = joinPoint.getSignature().toShortString();
        if (Strings.isEmpty(message))
            log.debug("Method {} execution: {} ms", methodName, executionTime);
        else
            log.debug("{}: {} ms", message, executionTime);
        return proceed;
    }
}
