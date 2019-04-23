package com.beyond.yili.report.yilireportweb.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.beyond.yili.dao.OracleDao;
import com.beyond.yili.report.yilireportweb.infra.YiliInfra;
import com.beyond.yili.util.RedisUtil;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author vipliliping
 * @create 2019/4/23 11:34
 * @desc
 **/
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    protected YiliInfra yiliInfra;
    @Autowired
    OracleDao oracleDao;

    public AuthInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            String yiliToken = request.getParameter("yili-token");
            String pathName = request.getRequestURI();
            if ("gmvcatedetail".equals(pathName.replace("/", "")) || "sicatedetail".equals(pathName.replace("/", "")) || "gmvplatdetail".equals(pathName.replace("/", "")) || "siplatdetail".equals(pathName.replace("/", ""))) {
                if (!StringUtils.isNotBlank(yiliToken)) {
                    log.error("未获得yilitoken");
                    response.setStatus(401);
                    return false;
                }

                String redis_userid = null;

                try {
                    redis_userid = RedisUtil.getString("yili-token-" + yiliToken);
                } catch (Exception var11) {
                    log.warn("Redis fail.", var11);
                }

                if (StringUtils.isNotBlank(redis_userid) && !"null".equals(redis_userid)) {
                    try {
                        RedisUtil.setString(yiliToken, redis_userid, 1440000);
                    } catch (Exception e) {
                        log.warn("Redis fail.",e);
                    }

                    this.setDataAuthToRedis(redis_userid);
                } else {
                    Map<String, Object> userMap = this.yiliInfra.validateToken(yiliToken);
                    if (userMap != null) {
                        redis_userid = String.valueOf(userMap.get("userId"));
                        if (redis_userid == null || "null".equals(redis_userid)) {
                            log.error("未获得userid");
                            response.setStatus(401);
                            return false;
                        }

                        try {
                            RedisUtil.setString(yiliToken, redis_userid, 1440000);
                        } catch (Exception e) {
                            log.warn("Redis fail.", e);
                        }

                        this.setDataAuthToRedis(redis_userid);
                    }
                }
            }
        }

        return true;
    }

    private void setDataAuthToRedis(String redis_userid) {
        try {
            if (RedisUtil.getString(redis_userid + "-authMap") == null) {
                Map<String, String> dataAuthMap = this.yiliInfra.getDataAuthFromOracle(Integer.valueOf(redis_userid));
                dataAuthMap.put("user_id", redis_userid);
                RedisUtil.setString(redis_userid + "-authMap", JSONObject.toJSONString(dataAuthMap), 3600);
            }
        } catch (Exception e) {
            log.warn("Redis fail.", e);
        }

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}