package com.beyond.yili.report.yilireportweb.infra;

import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 11:43
 * @desc
 **/
public interface YiliInfra {
    String getUserIdByToken(String var1);

    Map<String, Object> validateToken(String var1);

    Map<String, String> getDataAuthFromOracle(Integer var1);

    String getDataAuthFromRedis(String[] var1, String var2);
}
