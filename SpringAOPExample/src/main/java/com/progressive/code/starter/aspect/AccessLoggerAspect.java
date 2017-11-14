package com.progressive.code.starter.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.progressive.code.starter.annotation.LogValue;
import com.progressive.code.starter.domain.AccessLog;
import com.progressive.code.starter.service.AccessLoggerService;

@Aspect
@Component
public class AccessLoggerAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessLoggerAspect.class);

	
	@Autowired
	private AccessLoggerService accessLoggerService;

	@SuppressWarnings("unchecked")
	@Around("@annotation(com.progressive.code.starter.annotation.LogAccess)")
    public Object logDataAccess(ProceedingJoinPoint joinPoint) throws Throwable {

        //First we need the method signature of the target class (name and paramter types)
        MethodSignature methodSig = (MethodSignature) joinPoint.getSignature();

        //We need to know what the final target class (here CustomerServiceImpl) is
        Object target = joinPoint.getTarget();
        @SuppressWarnings("rawtypes")
		Class targetClass = AopProxyUtils.ultimateTargetClass(target);

        //Then we need the method to be invoked on the target class
        Method method = targetClass.getDeclaredMethod(methodSig.getName(), methodSig.getParameterTypes());

        StringBuilder parameterAndValue = new StringBuilder();

        //Additionally, we want to get all arguments that have been passed to the joinPoint
        Object[] args = joinPoint.getArgs();

        //And we need all annotations for all parameters of the target method, this is
        //a two dimensional array as multiple parameters can have multiple annotations
        Annotation[][] annotations = method.getParameterAnnotations();

        //Finally we can iterate through all annotations and check if we find
        //a LogValue annotation. If we find such an annotation we will get
        //the parameter name from the LogValue annotation and the corresponding
        //value from the args array
        for(int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof LogValue) {
                    LogValue logValue = (LogValue) annotation;
                    if (parameterAndValue.length() > 0) {
                    	parameterAndValue.append(",");
                    }
                    parameterAndValue.append(logValue.value()).append("=").append(args[i].toString());
                }
            }
        }

        //Now we can invoke the AccessLoggerService to log the data access
        accessLoggerService.logAccess(new AccessLog(getUserName(), targetClass.getName(), method.getName(), parameterAndValue.toString()));

        Object invocationResult = joinPoint.proceed();
        
        logInvocationResult(invocationResult);

        return invocationResult;
	}

	
	@SuppressWarnings("unchecked")
	private void logInvocationResult(Object invocationResult) {
		if (invocationResult != null) {
        	if (invocationResult instanceof Collection) {
        		@SuppressWarnings("rawtypes")
				Collection col = (Collection) invocationResult;
        		LOGGER.info("Collection with {} elements returned", col.size());
        		col.forEach(obj -> {
        			LOGGER.info(obj.toString());
        		});
        	} else {
        		LOGGER.info("Result: {}", invocationResult.toString());
        	}
        } else {
        	LOGGER.info("No result!");
        }
	}

	
	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
		    return authentication.getName();
		}
		return null;
	}

}