package com.thiagoti.easypay.domain.aop;

import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.util.Arrays;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class ValidationAspect {

    private final Validator validator;

    @Before("execution(* com.thiagoti.easypay..*.*(.., @jakarta.validation.Valid (*), ..))")
    public void validate(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            long count = Arrays.stream(joinPoint
                            .getTarget()
                            .getClass()
                            .getMethod(methodName, parameterTypes)
                            .getParameterAnnotations())
                    .flatMap(Arrays::stream)
                    .filter(a -> a.annotationType().equals(Valid.class))
                    .count();
            if (count > 0l) {
                log.debug("validating {}", arg);
                Set<ConstraintViolation<Object>> constraints = validator.validate(arg);

                if (Boolean.FALSE.equals(constraints.isEmpty())) {
                    throw new BusinessRuleException("invalid operation.", constraints);
                }
            }
        }
    }
}
