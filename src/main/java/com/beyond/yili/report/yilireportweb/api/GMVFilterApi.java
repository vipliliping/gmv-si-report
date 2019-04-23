package com.beyond.yili.report.yilireportweb.api;

import com.beyond.yili.common.util.FltParaUtil;
import com.beyond.yili.common.util.Result;
import com.beyond.yili.report.yilireportweb.infra.YiliKylinFilterInfra;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 16:22
 * @desc
 **/
@Controller
@Api(
        value = "GMV筛选器",
        description = "GMV筛选器API"
)
public class GMVFilterApi {
    private static final Logger log = LoggerFactory.getLogger(GMVFilterApi.class);
    @Autowired
    private YiliKylinFilterInfra yiliKylinFilterInfra;

    public GMVFilterApi() {
    }

    @ApiOperation(
            value = "GMV-品类筛选器",
            notes = "GMV-品类筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/gmv/filter/catePL"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result gmvCatePL(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        params.put("pg_日期", dateParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "GMV/GMV筛选器/品类.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "GMV-品牌筛选器",
            notes = "GMV-品牌筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "pl",
            value = "品类",
            required = false,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/gmv/filter/catePP"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result gmvCatePP(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String pl) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String plParam = FltParaUtil.stringToString(pl);
        params.put("pg_日期", dateParam);
        params.put("pg_品类", plParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "GMV/GMV筛选器/品牌.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "GMV-SKU筛选器",
            notes = "GMV-SKU筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "pl",
            value = "品类",
            required = false,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "pp",
            value = "品牌",
            required = false,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/gmv/filter/cateSKU"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result gmvCateSKU(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String dnDate, String cnDate, String pl, String pp) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String plParam = FltParaUtil.stringToString(pl);
        String ppParam = FltParaUtil.stringToString(pp);
        params.put("pg_日期", dateParam);
        params.put("pg_品类", plParam);
        params.put("pg_品牌", ppParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "GMV/GMV筛选器/SKU.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "GMV-段位筛选器",
            notes = "GMV-段位筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "pl",
            value = "品类",
            required = false,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "pp",
            value = "品牌",
            required = false,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "SKU",
            value = "sku",
            required = false,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/gmv/filter/cateDW"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result gmvCateDW(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String pl, String pp, String sku) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String plParam = FltParaUtil.stringToString(pl);
        String ppParam = FltParaUtil.stringToString(pp);
        String skuParam = FltParaUtil.stringToString(sku);
        params.put("pg_日期", dateParam);
        params.put("pg_品类", plParam);
        params.put("pg_品牌", ppParam);
        params.put("pg_SKU", skuParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "GMV/GMV筛选器/段位.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "GMV-平台筛选器",
            notes = "GMV-平台筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/gmv/filter/catePT"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result gmvCatePT(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        params.put("pg_日期", dateParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "GMV/GMV筛选器/平台.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "GMV-店铺筛选器",
            notes = "GMV-店铺筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "pt",
            value = "平台",
            required = false,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/gmv/filter/cateDP"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result gmvCateDP(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String pt) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String ptParam = FltParaUtil.stringToString(pt);
        params.put("pg_日期", dateParam);
        params.put("pg_平台", ptParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "GMV/GMV筛选器/店铺.ftl");
        return Result.ok(resultList);
    }
}