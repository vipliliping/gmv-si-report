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
 * @create 2019/4/23 16:57
 * @desc
 **/
@Controller
@Api(
        value = "SI筛选器",
        description = "SI筛选器API"
)
public class SIFilterApi {
    private static final Logger log = LoggerFactory.getLogger(SIFilterApi.class);
    @Autowired
    private YiliKylinFilterInfra yiliKylinFilterInfra;

    public SIFilterApi() {
    }

    @ApiOperation(
            value = "SI-品类筛选器",
            notes = "SI-品类筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/si/filter/catePL"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result siCatePL(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        params.put("pg_日期", dateParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "SI/SI筛选器/品类.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "SI-品牌筛选器",
            notes = "SI-品牌筛选器API"
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
            value = {"/si/filter/catePP"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result siCatePP(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String pl) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String plParam = FltParaUtil.stringToString(pl);
        params.put("pg_日期", dateParam);
        params.put("pg_品类", plParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "SI/SI筛选器/品牌.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "SI-SKU筛选器",
            notes = "SI-SKU筛选器API"
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
            value = {"/si/filter/cateSKU"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result siCateSKU(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String dnDate, String cnDate, String pl, String pp) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String plParam = FltParaUtil.stringToString(pl);
        String ppParam = FltParaUtil.stringToString(pp);
        params.put("pg_日期", dateParam);
        params.put("pg_品类", plParam);
        params.put("pg_品牌", ppParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "SI/SI筛选器/SKU.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "SI-段位筛选器",
            notes = "SI-段位筛选器API"
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
            value = {"/si/filter/cateDW"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result siCateDW(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String pl, String pp, String sku) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String plParam = FltParaUtil.stringToString(pl);
        String ppParam = FltParaUtil.stringToString(pp);
        String skuParam = FltParaUtil.stringToString(sku);
        params.put("pg_日期", dateParam);
        params.put("pg_品类", plParam);
        params.put("pg_品牌", ppParam);
        params.put("pg_SKU", skuParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "SI/SI筛选器/段位.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "SI-平台筛选器",
            notes = "SI-平台筛选器API"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "date",
            value = "日期",
            required = true,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/si/filter/catePT"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result siCatePT(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        params.put("pg_日期", dateParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "SI/SI筛选器/平台.ftl");
        return Result.ok(resultList);
    }

    @ApiOperation(
            value = "SI-店铺筛选器",
            notes = "SI-店铺筛选器API"
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
            value = {"/si/filter/cateDP"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result siCateDP(@ApiParam(name = "params",hidden = true) Map<String, Object> params, String date, String pt) throws Exception {
        String dateParam = FltParaUtil.dateFormat(date);
        String ptParam = FltParaUtil.stringToString(pt);
        params.put("pg_日期", dateParam);
        params.put("pg_平台", ptParam);
        List<String> resultList = this.yiliKylinFilterInfra.queryKylinDataByTemplateForList(params, "SI/SI筛选器/店铺.ftl");
        return Result.ok(resultList);
    }
}