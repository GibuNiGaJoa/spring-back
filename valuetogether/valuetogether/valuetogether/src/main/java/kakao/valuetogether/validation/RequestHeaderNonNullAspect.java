package kakao.valuetogether.validation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class RequestHeaderNonNullAspect {

    @Before("execution(public * *(.., @RequestHeaderNotNull (*), ..))")
    public void doBeforeJoinPoint(JoinPoint thisJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
        Annotation[][] annotations = signature.getMethod().getParameterAnnotations();

        int argIndex = 0;
        for (Object arg : thisJoinPoint.getArgs()) {
            RequestHeader requestHeader = null;
            RequestHeaderNonNull requestHeaderNonNull = null;
            Class<?> parameterType = null;

            for (Annotation annotation : annotations[argIndex]) {
                if (annotation.annotationType() == RequestHeader.class)
                    requestHeader = (RequestHeader) annotation;
                if (annotation.annotationType() == RequestHeaderNonNull.class) {
                    parameterType = signature.getMethod().getParameterTypes()[argIndex];
                    if (arg == null || (parameterType == String.class && arg.equals(""))) {
                        requestHeaderNonNull = (RequestHeaderNonNull) annotation;
                    }
                }
            }

            if (requestHeader != null && requestHeader.required() && requestHeaderNonNull != null) {
                throw new RequestHeaderWithNullValueException(requestHeader.value(),
                        new MethodParameter(signature.getMethod(), argIndex));
            }

            argIndex++;
        }
    }


}