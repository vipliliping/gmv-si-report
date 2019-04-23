package com.beyond.yili.report.yilireportweb.util;

/**
 * @author vipliliping
 * @create 2019/4/23 13:58
 * @desc
 **/
public enum ApiToTablesEnum {
    GMVCategoryApi("PJ_EC_SALES_GMV"),
    GMVPlatformApi("PJ_EC_SALES_GMV"),
    SICategoryApi("PJ_EC_SALES_SI"),
    SIPlatformApi("PJ_EC_SALES_SI"),
    GMVFilterApi("PJ_EC_SALES_GMV"),
    SIFilterApi("PJ_EC_SALES_SI");

    private String tableName;

    private ApiToTablesEnum(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }
}
