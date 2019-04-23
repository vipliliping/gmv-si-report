package com.beyond.yili.report.yilireportweb.infra.impl;

import com.beyond.sql.engine.TemplateUtil;
import com.beyond.yili.common.util.FltParaUtil;
import com.beyond.yili.common.util.RandomUtils;
import com.beyond.yili.dao.KylinDao;
import com.beyond.yili.report.yilireportweb.infra.TreeGridInfra;
import com.beyond.yili.report.yilireportweb.model.base.TreeGridHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author vipliliping
 * @create 2019/4/23 11:48
 * @desc
 **/
@Service
public class TreeGridImpl implements TreeGridInfra {
    private static final Logger log = LoggerFactory.getLogger(TreeGridImpl.class);
    @Autowired
    private KylinDao kylinDao;

    public TreeGridImpl() {
    }

    public List<Map<String, String>> queryKylinDataByTemplateForTree(Map params, String id, String parentId, String[] cols, String ftlFileName, String level, String treeValue) throws Exception {
        TemplateUtil templateUtil = new TemplateUtil();
        String[] colsTemp;
        if ("0".equals(level)) {
            colsTemp = new String[Integer.parseInt(level) + 1];
            System.arraycopy(cols, 0, colsTemp, 0, Integer.parseInt(level) + 1);
            params.put("cols", colsTemp);
        } else if (!"".equals(level)) {
            colsTemp = new String[Integer.parseInt(level) + 1];
            System.arraycopy(cols, 0, colsTemp, 0, Integer.parseInt(level) + 1);
            params.put("cols", colsTemp);
            String[] treeValueArr = treeValue.split("_");

            for (int i = 1; i <= Integer.parseInt(level); ++i) {
                params.put("pg_" + colsTemp[i - 1], FltParaUtil.stringToString(treeValueArr[i]));
            }
        }

        String sql = templateUtil.generateTemplateSql(ftlFileName, params);
        Map<String, Object> kylinData = this.kylinDao.getData(sql);
        List<Map<String, String>> dataList = new ArrayList();
        List<String[]> datas = (List) kylinData.get("data");
        Iterator var13 = datas.iterator();

        while (var13.hasNext()) {
            String[] data = (String[]) var13.next();
            Map<String, String> map = new HashMap();
            String id_temp = "总计";

            for (int j = 0; j < cols.length; ++j) {
                map.put(cols[j], data[j]);
                if ("".equals(level) && j == 0) {
                    map.put(cols[j], "总计");
                } else if (!"".equals(level) && j <= Integer.parseInt(level)) {
                    id_temp = id_temp + "_" + data[j];
                }

                if (!"".equals(level) && Integer.parseInt(level) > 0 && j == Integer.parseInt(level)) {
                    map.put(cols[0], data[j]);
                }
            }

            map.put(id, id_temp);
            map.put(parentId, treeValue);
            dataList.add(map);
        }

        return dataList;
    }

    public List<Map<String, String>> queryDataForTest(Map params, String[] cols, String ftlFileName, String level, String treeValue) throws Exception {
        Map<String, Object> kylinData = null;
        List<Map<String, String>> dataList = new ArrayList();
        if (level != null && !"".equals(level)) {
            if ("0".equals(level)) {
                kylinData = this.testKylinDataDaqu(cols);
            } else if ("1".equals(level)) {
                if ("粤海".equals(treeValue)) {
                    kylinData = this.testKylinDataYueHai(cols);
                } else {
                    kylinData = this.testKylinDataSuWan(cols);
                }
            } else {
                if (!"2".equals(level)) {
                    return new ArrayList();
                }

                kylinData = this.testKylinDataChaoShan(cols);
            }
        } else {
            kylinData = this.testKylinDataZongji(cols);
        }

        List<String[]> datas = (List) kylinData.get("data");
        Iterator var9 = datas.iterator();

        while (var9.hasNext()) {
            String[] data = (String[]) var9.next();
            Map<String, String> map = new HashMap();

            for (int j = 0; j < cols.length; ++j) {
                map.put(cols[j], data[j]);
            }

            map.put((new TreeGridHead()).getParentId(), treeValue);
            dataList.add(map);
        }

        return dataList;
    }

    private Map<String, Object> testKylinDataZongji(String[] cols) {
        Map<String, Object> map = new HashMap();
        List<String[]> data = new ArrayList();
        String[] colData1 = new String[]{"总计"};

        for (int i = 0; i < colData1.length; ++i) {
            String[] temp = new String[15];
            temp[0] = colData1[i];
            temp[5] = "我是" + colData1[i] + "产品描述";

            for (int j = 6; j < 11; ++j) {
                temp[j] = String.valueOf(RandomUtils.getRandom(5, 0));
                if (j == 10 || j == 9) {
                    temp[j] = String.valueOf(RandomUtils.getRandom(0, 4));
                }
            }

            data.add(temp);
        }

        map.put("cols", cols);
        map.put("data", data);
        return map;
    }

    private Map<String, Object> testKylinDataDaqu(String[] cols) {
        Map<String, Object> map = new HashMap();
        List<String[]> data = new ArrayList();
        String[] colData1 = new String[]{"粤海", "苏皖"};

        for (int i = 0; i < colData1.length; ++i) {
            String[] temp = new String[15];
            temp[0] = colData1[i];
            temp[5] = "我是" + colData1[i] + "产品描述";

            for (int j = 6; j < 11; ++j) {
                temp[j] = String.valueOf(RandomUtils.getRandom(5, 0));
                if (j == 10 || j == 9) {
                    temp[j] = String.valueOf(RandomUtils.getRandom(0, 4));
                }
            }

            data.add(temp);
        }

        map.put("cols", cols);
        map.put("data", data);
        return map;
    }

    private Map<String, Object> testKylinDataYueHai(String[] cols) {
        Map<String, Object> map = new HashMap();
        List<String[]> data = new ArrayList();
        String[] colData1 = new String[]{"潮汕", "东莞", "佛山", "广州", "海南", "惠州", "深圳", "中山"};

        for (int i = 0; i < colData1.length; ++i) {
            String[] temp = new String[15];
            temp[0] = colData1[i];
            temp[5] = "我是" + colData1[i] + "产品描述";

            for (int j = 6; j < 11; ++j) {
                temp[j] = String.valueOf(RandomUtils.getRandom(5, 0));
                if (j == 10 || j == 9) {
                    temp[j] = String.valueOf(RandomUtils.getRandom(0, 4));
                }
            }

            data.add(temp);
        }

        map.put("cols", cols);
        map.put("data", data);
        return map;
    }

    private Map<String, Object> testKylinDataSuWan(String[] cols) {
        Map<String, Object> map = new HashMap();
        List<String[]> data = new ArrayList();
        String[] colData1 = new String[]{"蚌埠", "合肥", "南京", "南京直营", "苏州", "芜湖"};

        for (int i = 0; i < colData1.length; ++i) {
            String[] temp = new String[15];
            temp[0] = colData1[i];
            temp[5] = "我是" + colData1[i] + "产品描述";

            for (int j = 6; j < 11; ++j) {
                temp[j] = String.valueOf(RandomUtils.getRandom(5, 0));
                if (j == 10 || j == 9) {
                    temp[j] = String.valueOf(RandomUtils.getRandom(0, 4));
                }
            }

            data.add(temp);
        }

        map.put("cols", cols);
        map.put("data", data);
        return map;
    }

    private Map<String, Object> testKylinDataChaoShan(String[] cols) {
        Map<String, Object> map = new HashMap();
        List<String[]> data = new ArrayList();
        String[] colData1 = new String[]{"潮潮", "汕汕"};

        for (int i = 0; i < colData1.length; ++i) {
            String[] temp = new String[15];
            temp[0] = colData1[i];
            temp[5] = "我是" + colData1[i] + "产品描述";

            for (int j = 6; j < 11; ++j) {
                temp[j] = String.valueOf(RandomUtils.getRandom(5, 0));
                if (j == 10 || j == 9) {
                    temp[j] = String.valueOf(RandomUtils.getRandom(0, 4));
                }
            }

            data.add(temp);
        }

        map.put("cols", cols);
        map.put("data", data);
        return map;
    }
}
