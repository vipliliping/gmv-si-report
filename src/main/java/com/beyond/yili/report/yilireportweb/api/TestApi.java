package com.beyond.yili.report.yilireportweb.api;

import com.beyond.yili.common.util.FormatEnum;
import com.beyond.yili.common.util.Result;
import com.beyond.yili.common.util.TreeGridRequestEnum;
import com.beyond.yili.report.yilireportweb.infra.TreeGridInfra;
import com.beyond.yili.report.yilireportweb.model.base.TreeGridHead;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 16:11
 * @desc
 **/
@Controller
@Api(
        value = "测试Api",
        description = "测试Api详情"
)
public class TestApi {
    private static final Logger log = LoggerFactory.getLogger(TestApi.class);
    @Autowired
    private TreeGridInfra treeGridInfra;

    public TestApi() {
    }

    @ApiOperation(value = "测试接口", notes = "测试接口详情")
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
            value = {"/test"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Object test(TreeGridRequestEnum typeEnum, String level, String treeValue, HttpServletRequest request) throws Exception {
        String[] cols = this.getCols();
        String[] colsKey = this.getColskey1();
        switch (typeEnum) {
            case HEAD:
                String[] columnFormat = new String[]{FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_FLOAT.code, FormatEnum.FORMAT_FLOAT_TWO.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_RATE.code, FormatEnum.FORMAT_RATE_ARROW.code};
                String[] excludeCols = new String[]{"区域名称", "经销商名称", "子品牌", "冷饮产品属性"};
                TreeGridHead treeGridHead = new TreeGridHead(3, cols, colsKey, excludeCols, columnFormat);
                return Result.ok(treeGridHead);
            case BODY:
                List<Map<String, String>> dataList = this.treeGridInfra.queryDataForTest(new HashMap(), colsKey, "GMV/GMV平台明细.ftl", level, treeValue);
                return dataList;
            default:
                return Result.failure("请求类型错误！");
        }
    }

    @ApiOperation(
            value = "测试接口2",
            notes = "测试接口2详情"
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
            value = {"/test2"},
            method = {RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Object test2(TreeGridRequestEnum typeEnum, String level, String treeValue, HttpServletRequest request) throws Exception {
        String[] cols = this.getCols();
        String[] colsKey = this.getColskey2();
        switch (typeEnum) {
            case HEAD:
                String[] columnFormat = new String[]{FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_FLOAT.code, FormatEnum.FORMAT_FLOAT_TWO.code, FormatEnum.FORMAT_STRING.code, FormatEnum.FORMAT_RATE.code, FormatEnum.FORMAT_RATE_ARROW.code};
                String[] excludeCols = new String[]{"quyumingcheng", "jingxiaoshangmingcheng", "zipinpai", "lengyinchanpinshuxing"};
                String[][] columnGroupsArr = new String[][]{{"0", "6", "二级表头第一列"}, {"6", "11", "二级表头第二列"}};
                TreeGridHead treeGridHead = new TreeGridHead(2, cols, colsKey, excludeCols, columnFormat, columnGroupsArr);
                return Result.ok(treeGridHead);
            case BODY:
                List<Map<String, String>> dataList = this.treeGridInfra.queryDataForTest(new HashMap(), colsKey, "xxx.tfl", level, treeValue);
                return dataList;
            default:
                return Result.failure("请求类型错误！");
        }
    }

    private String[] getCols() {
        String[] cols = new String[]{"大区名称", "区域名称", "经销商名称", "子品牌", "冷饮产品属性", "产品描述", "本期销售", "销售目标", "本期达成", "同期销售", "同比增长"};
        return cols;
    }

    private String[] getColskey1() {
        String[] cols = new String[]{"大区名称", "区域名称", "经销商名称", "子品牌", "冷饮产品属性", "chanpinmiaoshu", "benqixiaoshou", "xiaoshoumubiao", "benqidacheng", "tongqixiaoshou", "tongbizengzhang"};
        return cols;
    }

    private String[] getColskey2() {
        String[] cols = new String[]{"daqumingchneg", "quyumingcheng", "jingxiaoshangmingcheng", "zipinpai", "lengyinchanpinshuxing", "chanpinmiaoshu", "benqixiaoshou", "xiaoshoumubiao", "benqidacheng", "tongqixiaoshou", "tongbizengzhang"};
        return cols;
    }
}
