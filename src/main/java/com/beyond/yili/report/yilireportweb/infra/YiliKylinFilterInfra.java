package com.beyond.yili.report.yilireportweb.infra;

import java.util.List;
import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 11:46
 * @desc
 **/
public interface YiliKylinFilterInfra {
    List<String> queryKylinDataByTemplateForList(Map<String, Object> var1, String var2) throws Exception;
}
