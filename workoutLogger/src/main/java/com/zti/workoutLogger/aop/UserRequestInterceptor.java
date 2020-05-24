package com.zti.workoutLogger.aop;

import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Aspect
@Configuration
public class UserRequestInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(UserRequestInterceptor.class);
    private final static Map<Class<? extends Annotation>, String> REQUEST_NAME_BY_ANNOTATION = new HashMap<>();

    static {
        REQUEST_NAME_BY_ANNOTATION.put(GetMapping.class, "GET");
        REQUEST_NAME_BY_ANNOTATION.put(PostMapping.class, "POST");
        REQUEST_NAME_BY_ANNOTATION.put(PutMapping.class, "PUT");
        REQUEST_NAME_BY_ANNOTATION.put(DeleteMapping.class, "DELETE");
    }

    @Autowired
    private AuthenticatedUserGetter userGetter;

    @Pointcut("execution(* com.zti.workoutLogger.controllers.*.*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info(String.format("User %s requests [%s] to: %s at %s", userGetter.get().getUsername(),
                getRequestTypeName(signature), signature.toShortString(), LocalDateTime.now()));
    }

    private String getRequestTypeName(MethodSignature signature) {
        Optional<String> name = REQUEST_NAME_BY_ANNOTATION.entrySet().stream()
                .filter(entry -> signature.getMethod().getAnnotation(entry.getKey()) != null)
                .map(Map.Entry::getValue)
                .findFirst();
        return name.orElseThrow(IllegalStateException::new);
    }
}