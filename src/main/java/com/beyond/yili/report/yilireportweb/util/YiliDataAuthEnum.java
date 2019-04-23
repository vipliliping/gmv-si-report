package com.beyond.yili.report.yilireportweb.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 13:57
 * @desc
 **/
public enum YiliDataAuthEnum {
    W_PRODUCT_D("W_PRODUCT_D.PRODUCT_CHILD_BRAND"),
    DM_PRODUCT_KYLIN_D("DM_PRODUCT_KYLIN_D.PRODUCT_CHILD_BRAND"),
    DM_PRODUCT_KYLIN("DM_PRODUCT_KYLIN.PRODUCT_CHILD_BRAND"),
    W_PRODUCT_KYLIN_D("W_PRODUCT_KYLIN_D.PRODUCT_CHILD_BRAND"),
    PJ_SALES_PROD_VIEW_D("PJ_SALES_PROD_VIEW_D.PRODUCT_CHILD_BRAND"),
    DM_KA_SYSTEM("DM_KA_SYSTEM.KA_SYSTEM_NM"),
    DM_SL_WAREHOUSE("DM_SL_WAREHOUSE.WH_CODE"),
    DM_SL_WAREHOUSE_D("DM_SL_WAREHOUSE_D.WH_CODE"),
    W_SL_WAREHOUSE_D("W_SL_WAREHOUSE_D.WH_CODE"),
    DM_SL_REQUEST_TYPE("DM_SL_REQUEST_TYPE.LOOKUP_CODE"),
    DM_SL_REQUEST_TYPE_D("DM_SL_REQUEST_TYPE_D.LOOKUP_CODE"),
    W_SL_REQUEST_TYPE_D("W_SL_REQUEST_TYPE_D.WH_CODE");

    private String field;

    private YiliDataAuthEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return this.field;
    }

    public static Map<String, String> getYiliDataAuthMap() {
        Map<String, String> map = new HashMap();
        YiliDataAuthEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            YiliDataAuthEnum authEnum = var1[var3];
            map.put(authEnum.name(), authEnum.getField());
        }

        return map;
    }
}
