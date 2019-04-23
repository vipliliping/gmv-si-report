package com.beyond.yili.report.yilireportweb.infra;

import java.util.List;
import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 11:44
 * @desc
 **/
public interface TreeGridInfra {
    List<Map<String, String>> queryKylinDataByTemplateForTree(Map var1, String var2, String var3, String[] var4, String var5, String var6, String var7) throws Exception;

    List<Map<String, String>> queryDataForTest(Map var1, String[] var2, String var3, String var4, String var5) throws Exception;
}
