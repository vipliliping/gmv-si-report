package com.beyond.yili.report.yilireportweb.aop;

import com.beyond.yili.common.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author vipliliping
 * @create 2019/4/23 17:20
 * @desc
 **/
@Component
@Aspect
public class DurationAspect {
    private static final Logger log = LoggerFactory.getLogger(DurationAspect.class);
    static final String DURATION_CUT = "execution(com.beyond.yili.common.util.Result com.beyond.yili.report.yilireportweb.api..*.*(..))";

    public DurationAspect() {
    }

    @Pointcut("execution(com.beyond.yili.common.util.Result com.beyond.yili.report.yilireportweb.api..*.*(..))")
    public void durationPointcut() {
    }

    @Before("durationPointcut()")
    public void doBefore() {
    }

    @Around("durationPointcut()")
    public Result doAfter(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();

        try {
            Result result = (Result)proceedingJoinPoint.proceed();
            long endTime = System.currentTimeMillis();
            result.setDuration((double)(endTime - startTime));
            log.debug("{}\tuse time:{}ms", proceedingJoinPoint, endTime - startTime);
            return result;
        } catch (Throwable var7) {
            var7.printStackTrace();
            return Result.unknown(var7.getMessage());
        }
    }
}

