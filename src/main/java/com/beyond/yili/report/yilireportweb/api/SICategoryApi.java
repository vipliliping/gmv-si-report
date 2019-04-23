package com.beyond.yili.report.yilireportweb.api;

import com.beyond.yili.common.util.FltParaUtil;
import com.beyond.yili.common.util.FormatEnum;
import com.beyond.yili.common.util.Result;
import com.beyond.yili.common.util.TreeGridRequestEnum;
import com.beyond.yili.report.yilireportweb.infra.TreeGridInfra;
import com.beyond.yili.report.yilireportweb.model.base.Column;
import com.beyond.yili.report.yilireportweb.model.base.TreeGridHead;
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
 * @create 2019/4/23 16:40
 * @desc
 **/
@Controller
@Api(
        value = "SI品类明细",
        description = "SI品类明细Api"
)
public class SICategoryApi {
    private static final Logger log = LoggerFactory.getLogger(SICategoryApi.class);
    private String[] cols = new String[]{"品类", "品牌", "SKU", "平台", "店铺", "成交商品件数", "成交金额", "平均件单价"};
    private String[] colKeys = new String[]{"品类", "品牌", "SKU", "平台", "店铺", "成交商品件数", "成交金额", "平均件单价"};
    private String[] excludeCols = new String[]{"品牌", "SKU", "平台", "店铺"};
    private String[] columnFormat;
    @Autowired
    private TreeGridInfra treeGridInfra;

    public SICategoryApi() {
        this.columnFormat = new String[]{FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_FLOAT.code, FormatEnum.FORMAT_FLOAT_TWO.code, FormatEnum.FORMAT_FLOAT_TWO.code};
    }

    @ApiOperation(
            value = "SI-Category",
            notes = "SI品类明细表头和表格接口"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "typeEnum",
            value = "表头初始化或者数据查询",
            required = true,
            dataType = "String",
            dataTypeClass = TreeGridRequestEnum.class,
            paramType = "query"
    ), @ApiImplicitParam(
            name = "level",
            value = "层级",
            required = false,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "treeValue",
            value = "分组查询值",
            required = false,
            dataType = "String",
            paramType = "query"
    )})
    @RequestMapping(
            value = {"/si/cate/detail"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Object siCate(@ApiParam(name = "params",hidden = true) Map<String, Object> params, TreeGridRequestEnum typeEnum, String level, String treeValue, String date, String timeWD, String cates, String brands, String skus, String ranks, String plats, String shops) throws Exception {
        TreeGridHead treeGridHead = new TreeGridHead(5, this.cols, this.colKeys, this.excludeCols, this.columnFormat);
        ((Column)treeGridHead.getColumns().get(0)).setWidth("300");
        switch(typeEnum) {
            case HEAD:
                return Result.ok(treeGridHead);
            case BODY:
                params.put("pg_日期", FltParaUtil.dateFormat(date));
                params.put("pg_时间颗粒度", timeWD);
                params.put("pg_品类", FltParaUtil.stringToString(cates));
                params.put("pg_品牌", FltParaUtil.stringToString(brands));
                params.put("pg_SKU", FltParaUtil.stringToString(skus));
                params.put("pg_段位", FltParaUtil.stringToString(ranks));
                params.put("pg_平台", FltParaUtil.stringToString(plats));
                params.put("pg_店铺", FltParaUtil.stringToString(shops));
                List<Map<String, String>> dataList = this.treeGridInfra.queryKylinDataByTemplateForTree(params, treeGridHead.getId(), treeGridHead.getParentId(), this.colKeys, "SI/SI品类明细.ftl", level, treeValue);
                return dataList;
            default:
                return Result.failure("请求类型错误！");
        }
    }
}
