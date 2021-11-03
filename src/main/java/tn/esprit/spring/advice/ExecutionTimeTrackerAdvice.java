package tn.esprit.spring.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {

Logger logger=LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);
	
	@Around("@annotation(tn.esprit.spring.advice.TrackExecutionTime)")
	public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
		long stratTime=System.currentTimeMillis();
		Object obj=pjp.proceed();
		long endTime=System.currentTimeMillis();
		logger.info("Method name:  "+pjp.getSignature()+" take time to execute : "+(endTime-stratTime));
		return obj;
	}
}
