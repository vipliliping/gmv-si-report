<#--ver06-->
<#-- [0]页面变量初始化区-->
<#if !pg_时间颗粒度??||pg_时间颗粒度="'all'"><#assign pg_时间颗粒度 = ""></#if>
<#if !pg_品类??||pg_品类="'all'"><#assign pg_品类 = ""></#if>
<#if !pg_品牌??||pg_品牌="'all'"><#assign pg_品牌 = ""></#if>
<#if !pg_SKU??||pg_SKU="'all'"><#assign pg_SKU = ""></#if>
<#if !pg_段位??||pg_段位="'all'"><#assign pg_段位 = ""></#if>
<#if !pg_平台??||pg_平台="'all'"><#assign pg_平台 = ""></#if>
<#if !pg_店铺??||pg_店铺="'all'"><#assign pg_店铺 = ""></#if>

<#if !pg_日期??||pg_日期="'all'"><#assign pg_日期 = "20190317"></#if>

<#-- [1]Cube级别通用数据字典定义区 -->
<#-- [1.1]基础指标变量定义区 -->
<#assign 日序号 ="W_DATE_D.DAY_NUM">
<#assign 月天数 ="W_DATE_D.MONTH_DAYS">
<#assign 月序号 ="W_DATE_D.MONTH_NUM">
<#assign 年序号 ="W_DATE_D.YEAR_NUM">
<#assign 品类 ="pj_ec_sales_si.pro_categ_name">
<#assign 事业部 ="W_BG_D.BG_NAME">
<#assign 品牌 ="pj_ec_sales_si.pro_brand_name">
<#assign SKU ="W_EC_PRODUCT_D1.erp_name">
<#assign 段位 ="pj_ec_sales_si.pro_grade">
<#assign 平台 ="pj_ec_sales_si.platform_name">
<#assign 店铺 ="pj_ec_sales_si.sale_area_name">
<#assign 日期 ="W_DATE_D.ROW_WID">

<#assign SI日累计 ="pj_ec_sales_si.amt_bf_without_tax">
<#assign SI月累计 ="pj_ec_sales_si.amt_bf_without_tax_mtd">
<#assign SI日目标值 ="pj_ec_sales_si.budget_amt_bf_without_tax">
<#assign SI月目标值 ="pj_ec_sales_si.budget_amt_bf_without_tax_mtd">
<#assign SI日同期 ="pj_ec_sales_si.yago_amt_bf_without_tax">
<#assign SI月同期 ="pj_ec_sales_si.yago_amt_bf_without_tax_mtd">
<#assign SI完成度目标值 ="pj_ec_sales_si.complete_percent_target">
<#assign SI日成交商品件数 ="pj_ec_sales_si.sales_qty_box">
<#assign SI月成交商品件数 ="pj_ec_sales_si.sales_qty_box_mtd">

<#-- [1.2]简单计算指标变量及宏定义区 -->

<#assign smpl_经营指标="SI日累计/10000">
<#macro @smpl_经营指标>
    <#switch pg_时间颗粒度>
        <#case "当日" >  <#assign smpl_经营指标="SI日累计/10000"> <#break>
        <#case "MTD" >  <#assign smpl_经营指标="SI月累计/10000"> <#break>
        <#case "YTD" >  <#assign smpl_经营指标="SI年累计/10000"> <#break>
    </#switch>
</#macro>

<#assign smpl_预算目标="SI月目标值/10000">
<#macro @smpl_预算目标>
    <#switch pg_时间颗粒度>
        <#case "MTD" >  <#assign smpl_预算目标="SI月目标值/10000"> <#break>
        <#case "YTD" >  <#assign smpl_预算目标="SI年目标值/10000"> <#break>
    </#switch>
</#macro>

<#assign smpl_SI成交商品简述="SI日成交商品件数">
<#macro @smpl_SI成交商品简述>
    <#switch pg_时间颗粒度>
        <#case "当日" >  <#assign smpl_SI成交商品简述="SI日成交商品件数"> <#break>
        <#case "MTD" >  <#assign smpl_SI成交商品简述="SI月成交商品件数"> <#break>
        <#case "YTD" >  <#assign smpl_SI成交商品简述="SI年成交商品件数"> <#break>
    </#switch>
</#macro>


<#-- [1.3]简单计算指标宏运行区 -->

<@@smpl_经营指标/>
<@@smpl_预算目标/>
<@@smpl_SI成交商品简述/>

<#-- [1.4]smpl-smpl指标变量及宏定义区 -->

<#-- [1.5]smpl-smpl指标宏运行区 -->

<#-- [1.6]复杂表达式计算指标变量定义区 -->

<#-- [1.7]ocplx指标变量定义区 -->

<#assign ocplx_SI月完成度="SI月累计/nullif(SI月目标值,0)">
<#assign ocplx_SI年完成度="SI年累计/nullif(SI年目标值,0)">
<#assign ocplx_SI月同比="(SI月累计-SI月同期)/nullif(SI月同期,0)">
<#assign ocplx_SI年同比="(SI年累计-SI年同期)/nullif(SI年同期,0)">
<#assign ocplx_SI日平均单价="SI日累计/nullif(SI日成交商品件数,0)">
<#assign ocplx_SI月平均单价="SI月累计/nullif(SI月成交商品件数,0)">
<#assign ocplx_SI年平均单价="SI年累计/nullif(SI年成交商品件数,0)">

<#-- [1.8]osmpl指标变量定义区 -->
<#assign osmpl_SI平均单价="${ocplx_SI日平均单价}">
<#macro @osmpl_SI平均单价>
    <#switch pg_时间颗粒度>
        <#case "当日" >  <#assign osmpl_SI平均单价="${ocplx_SI日平均单价}"> <#break>
        <#case "MTD" >  <#assign osmpl_SI平均单价="${ocplx_SI月平均单价}"> <#break>
        <#case "YTD" >  <#assign osmpl_SI平均单价="${ocplx_SI年平均单价}"> <#break>
    </#switch>
</#macro>

<#assign osmpl_SI同比="${ocplx_SI月同比}">
<#macro @osmpl_SI同比>
    <#switch pg_时间颗粒度>
        <#case "MTD" >  <#assign osmpl_SI同比="${ocplx_SI月同比}"> <#break>
        <#case "YTD" >  <#assign osmpl_SI同比="${ocplx_SI年同比}"> <#break>
    </#switch>
</#macro>

<#assign osmpl_SI完成度="${ocplx_SI月完成度}">
<#macro @osmpl_SI完成度>
    <#switch pg_时间颗粒度>
        <#case "MTD" >  <#assign osmpl_SI完成度="${ocplx_SI月完成度}"> <#break>
        <#case "YTD" >  <#assign osmpl_SI完成度="${ocplx_SI年完成度}"> <#break>
    </#switch>
</#macro>

<#-- [1.9]osmpl指标变量宏运行区 -->

<@@osmpl_SI平均单价/>
<@@osmpl_SI同比/>
<@@osmpl_SI完成度/>



<#-- Where部分 -->

<#assign _ParameterDateWhereCondition>
<#-- [1.10.1]InnerSQL日期过滤条件 -->

 and ${日期}=${pg_日期}

</#assign>

<#assign _WhereCondition>
<#-- [1.10]Where筛选条件变量定义区--放到流程最后阶段处理-->



    <#if data_auth?? && data_auth!="">
        ${data_auth}
    </#if>

    <#if pg_品类!="">
and ${品类} in (${pg_品类})
    </#if>

    <#if pg_品牌!="">
and ${品牌} in (${pg_品牌})
    </#if>

    <#if pg_SKU!="">
and ${SKU} in (${pg_SKU})
    </#if>

    <#if pg_段位!="">
and ${段位} in (${pg_段位})
    </#if>

    <#if pg_平台!="">
and ${平台} in (${pg_平台})
    </#if>

    <#if pg_店铺!="">
and ${店铺} in (${pg_店铺})
    </#if>

</#assign>

<#-- [1.10]结束-->



<#--group代码部分-->
<#assign build_in_grpcols=[

"平台",
"店铺",
"品类",
"品牌",
"SKU"

]>

<#assign select_平台='1'>
<#assign select_店铺='1'>
<#assign select_品类='1'>
<#assign select_品牌='1'>
<#assign select_SKU='1'>


<#if !cols??>
    <#assign cols = [""]>
</#if>

<#list cols as col>
    <#switch col>
        <#case "平台" >  <#assign select_平台="${平台}"><#break>
        <#case "店铺" >  <#assign select_店铺="${店铺}"><#break>
        <#case "品类" >  <#assign select_品类="${品类}"><#break>
        <#case "品牌" >  <#assign select_品牌="${品牌}"><#break>
        <#case "SKU" >  <#assign select_SKU="${SKU}"><#break>
    </#switch>
</#list>

<#assign Group>
    ${select_平台}

    <#if select_店铺!='1'>
	,${select_店铺}
    </#if>

    <#if select_品类!='1'>
	,${select_品类}
    </#if>

    <#if select_品牌!='1'>
	,${select_品牌}
    </#if>

    <#if select_SKU!='1'>
	,${select_SKU}
    </#if>
</#assign>

<#--group代码部分-->
<#assign _GroupCondition>

    <#if select_平台 != '1'>
		group by

        ${Group}
    <#else>

    </#if>

</#assign>

<#-- [2]数据模型FTL内层SQL定义区 -->
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
with innerSQL as(

select
<#-- [2.1]内层SQL取用字段声明区 -->
${select_平台} as 平台,
${select_店铺} as 店铺,
${select_品类} as 品类,
${select_品牌} as 品牌,
${select_SKU} as  SKU,

<#-- [2.1.3]内层SQL基础指标取用字段声明区 -->
sum(${SI日累计})  as SI日累计,
sum(${SI月累计})  as SI月累计,
sum(${SI日成交商品件数})  as SI日成交商品件数,
sum(${SI月成交商品件数})  as SI月成交商品件数

<#-- [2.1.4]内层SQL简单计算指标取用字段声明区 -->




<#-- [2.1.5]内层SQL复杂计算指标取用字段声明区 -->

<#-- [2.1.6]内层SQL复杂计算指标CASE表达式代码区 -->

<#-- [2.2]内层SQL数据源使用区 -->
${commDataSrc}
<#-- [2.3]内层SQLwhere条件声明区 -->
where 1=1
${_ParameterDateWhereCondition}
${_WhereCondition}
<#-- [2.4]内层SQL group by条件声明区 -->
${_GroupCondition}
)


,Tbl_YTD as (
select
<#-- [2.5.2.1]内层YTD维度定义区 -->

${select_平台} as 平台,
${select_店铺} as 店铺,
${select_品类} as 品类,
${select_品牌} as 品牌,
${select_SKU} as  SKU,

<#-- [2.5.2.2]内层YTD指标定义区 -->

SUM(amt_bf_without_tax) as SI年累计,
SUM(sales_qty_box) as SI年成交商品件数


<#-- [2.5.2.3]内层YTD数据源使用区 -->
${commDataSrc}
<#-- [2.5.2.4.1]内层YTD where条件声明区 -->
where 1=1
${_WhereCondition}
<#-- [2.5.2.4.2]内层YTD 时间限定条件声明区 -->
<#-- 除了更改中文指标名称，此部分尽量不要修改-->
  and ${日期}<=${pg_日期}
  and ${年序号}=${pg_日期?substring(0,4)?number}
<#-- [2.5.2.5]内层YTD group by条件声明区 -->
${_GroupCondition}
)

,outerSQL as(
<#-- [2.6]外层SQL定义区 -->
select

<#-- [2.6.1]外层SQL维度字段取用字段声明区 -->
Tbl_YTD.平台,
Tbl_YTD.店铺,
Tbl_YTD.品类,
Tbl_YTD.品牌,
Tbl_YTD.SKU,

<#-- [2.6.2]外层SQL CASE表达式方式维度字段取用字段声明区 -->


<#-- [2.6.3]外层SQL基础指标取用字段声明区 -->
SI日累计 ,
SI月累计 ,
SI年累计 ,

SI日成交商品件数 ,
SI月成交商品件数 ,
SI年成交商品件数 ,

${smpl_经营指标} as 经营指标,
${smpl_SI成交商品简述} as SI成交商品简述


from Tbl_YTD
left join innerSQL on
<#-- innerSQL连接条件声明区 -->
   2=2
<#if func_grpcols??>
    <#list func_grpcols as grpcol>
  and Tbl_YTD.${grpcol}=innerSQL.${grpcol}
    </#list>
<#else>
    <#list build_in_grpcols as grpcol>
  and Tbl_YTD.${grpcol}=innerSQL.${grpcol}
    </#list>
</#if>

)


select

<#-- [2.6.1]外层SQL维度字段取用字段声明区 -->
平台,
店铺,
品类,
品牌,
SKU,




SI成交商品简述  as 成交商品件数,
经营指标  as 成交金额,

<#-- [2.6.4]外层SQL ocplx指标取用字段声明区 -->


<#-- [2.6.5]外层SQL复杂计算指标表达式代码区 -->
${osmpl_SI平均单价} as 平均件单价



from outerSQL