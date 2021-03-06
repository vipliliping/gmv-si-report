<#--ver06-->
<#-- [0]页面变量初始化区-->


<#if !pg_品类??||pg_品类="'all'"><#assign pg_品类 = ""></#if>

<#if !pg_日期??||pg_日期="'all'"><#assign pg_日期 = "20181231"></#if>
<#if !pg_当年??||pg_当年="'all'"><#assign pg_当年 = (pg_日期?substring(0,4))?eval*10000></#if>
<#if !pg_次年??||pg_次年="'all'"><#assign pg_次年 = ((pg_日期?substring(0,4))?eval+1)*10000></#if>

<#-- [1]Cube级别通用数据字典定义区 -->
<#-- [1.1]基础指标变量定义区 -->
<#assign 日序号 ="W_DATE_D.DAY_NUM">
<#assign 月天数 ="W_DATE_D.MONTH_DAYS">
<#assign 月序号 ="W_DATE_D.MONTH_NUM">
<#assign 年序号 ="W_DATE_D.YEAR_NUM">
<#assign 品类 ="pj_ec_sales_si.pro_categ_name">
<#assign 事业部 ="W_BG_D.BG_NAME">
<#assign 品牌 ="pj_ec_sales_si.pro_brand_name">
<#assign 品类五级 ="W_EC_PRODUCT_D1.erp_name">
<#assign 段位 ="pj_ec_sales_si.pro_grade">
<#assign 平台 ="pj_ec_sales_si.platform_name">
<#assign 细分渠道 ="pj_ec_sales_si.sale_area_name">
<#assign 日期 ="W_DATE_D.ROW_WID">

<#-- Where部分 -->
<#assign _WhereCondition>
<#-- [1.10]Where筛选条件变量定义区--放到流程最后阶段处理-->

    <#if data_auth?? && data_auth!="">
        ${data_auth}
    </#if>

and ${日期}>${pg_当年}
and ${日期}<${pg_次年}


    <#if pg_品类!="">
    and ${品类} in (${pg_品类})
    </#if>

</#assign>

<#-- [1.10]结束-->

<#-- [2.0]共用数据源定义声明区 --在流程后期阶段处理-->
<#assign commDataSrc="
from

BIGDATA_DM.PJ_EC_SALES_SI PJ_EC_SALES_SI
INNER JOIN BIGDATA_DW.W_DATE_D W_DATE_D
ON PJ_EC_SALES_SI.SALES_DT_WID = W_DATE_D.ROW_WID
INNER JOIN BIGDATA_DW.W_EC_PRODUCT_D1 W_EC_PRODUCT_D1
ON PJ_EC_SALES_SI.ERP_ID = W_EC_PRODUCT_D1.ERP_ID
INNER JOIN BIGDATA_DW.W_BG_D W_BG_D
ON PJ_EC_SALES_SI.BG_WID = W_BG_D.ROW_WID

">

select
${品牌} 品牌
${commDataSrc}

where 1=1
${_WhereCondition}
group by ${品牌}