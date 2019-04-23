package com.beyond.yili.report.yilireportweb.aop;

import com.beyond.yili.common.exception.ForbiddenException;
import com.beyond.yili.common.exception.NoAuthException;
import com.beyond.yili.report.yilireportweb.infra.YiliInfra;
import com.beyond.yili.report.yilireportweb.util.ApiToTablesEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 18:29
 * @desc
 **/
@Component
@Aspect
public class YiliTokenAspect {
    private static final Logger log = LoggerFactory.getLogger(YiliTokenAspect.class);
    @Autowired
    private YiliInfra yiliInfra;
    static final String TOKEN_CUT = "execution(* com.beyond.yili.report.yilireportweb.api..*.*(..))";

    public YiliTokenAspect() {
    }

    @Pointcut("execution(* com.beyond.yili.report.yilireportweb.api..*.*(..))")
    public void tokenPointcut() {
    }

    @Before("tokenPointcut()")
    public void doBefore(JoinPoint joinPoint) throws ForbiddenException, NoAuthException {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        String[] yiliTokenArr = (String[])requestParameterMap.get("yili-token");
        if (yiliTokenArr != null && yiliTokenArr.length != 0) {
            boolean isAuth = true;

            for(int i = 0; i < joinPoint.getArgs().length; ++i) {
                if (joinPoint.getArgs()[i] != null && "org.springframework.validation.support.BindingAwareModelMap".equals(joinPoint.getArgs()[i].getClass().getName())) {
                    log.info("aop 获取token为:{}", yiliTokenArr);
                    if (yiliTokenArr != null && StringUtils.isNotBlank(yiliTokenArr[0])) {
                        Map map = (Map)joinPoint.getArgs()[0];
                        String yiliUserId = this.yiliInfra.getUserIdByToken(yiliTokenArr[0]);
                        ApiToTablesEnum apiToTablesEnum = ApiToTablesEnum.valueOf(joinPoint.getTarget().getClass().getSimpleName());
                        if (apiToTablesEnum == null) {
                            throw new ForbiddenException("com.beyond.yili.report.yilireportweb.api.** aop 没有找到Api名称对应的数据权限表名");
                        }

                        log.debug("传出的表名为:{}", apiToTablesEnum.getTableName().split(","));
                        String dataAuth = this.yiliInfra.getDataAuthFromRedis(apiToTablesEnum.getTableName().split(","), yiliUserId);
                        map.put("data_auth", dataAuth);
                        isAuth = false;
                    }
                }
            }

            if (isAuth) {
                throw new ForbiddenException("com.beyond.yili.report.yilireportweb.api.** aop 没有找到Map参数，必须定义Map参数");
            }
        } else {
            throw new NoAuthException("没有获取到yili登录token");
        }
    }
}
