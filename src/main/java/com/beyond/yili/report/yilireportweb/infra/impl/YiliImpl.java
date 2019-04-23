package com.beyond.yili.report.yilireportweb.infra.impl;

import com.alibaba.fastjson.JSONObject;
import com.beyond.yili.dao.OracleDao;
import com.beyond.yili.report.yilireportweb.infra.YiliInfra;
import com.beyond.yili.report.yilireportweb.util.YiliDataAuthEnum;
import com.beyond.yili.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.beyond.yili.dao.OracleDao;

//import com.beyond.yili.dao.OracleDao;

/**
 * @author vipliliping
 * @create 2019/4/23 13:25
 * @desc
 **/
@Service
public class YiliImpl implements YiliInfra {
    private static final Logger log = LoggerFactory.getLogger(YiliImpl.class);
    @Value("${yili_token_url}")
    private String tokenUrl;
    @Autowired
    private OracleDao oracleDao;
//    private static String PJ_EC_SALES_GMV = "PJ_EC_SALES_GMV";
//    private static String PJ_EC_SALES_SI = "PJ_EC_SALES_SI";
    private static String[] PJ_EC_SALES_TABLE = {"PJ_EC_SALES_GMV", "PJ_EC_SALES_SI"};

    public String getUserIdByToken(String token) {
        String yiliUserId = null;
        try {
            yiliUserId = RedisUtil.getString("yili-token-" + token);
        } catch (Exception e) {
            log.warn("Redis fail.", e);
        }
        if (yiliUserId == null) {
            Map<String, Object> userMap = validateToken(token);
            if (userMap != null) {
                try {
                    yiliUserId = String.valueOf(userMap.get("userId"));
                    RedisUtil.setString("yili-token-" + token, yiliUserId, 86400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.debug("根据token:{}获取到的userId:{}", token, yiliUserId);
        return yiliUserId;
    }

    public Map<String, Object> validateToken(String token) {
        BufferedReader responseReader = null;
        URL restServiceURL;
        try {
            String url = this.tokenUrl + "?username=" + token;
            restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.connect();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
            }
            StringBuffer sb = new StringBuffer();
            responseReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
            String readLine;
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            Map<String, Map<String, Object>> map = (Map) JSONObject.parseObject(sb.toString(), Map.class);
            Map<String, Object> userMap = map.get("result");
            return userMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                responseReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> getDataAuthFromOracle(Integer userId) {
        Map<String, String> result = new HashMap();

        List<String> areaAuthList = this.oracleDao.getUserAreaAuth(userId);
        for (Iterator localIterator1 = areaAuthList.iterator(); localIterator1.hasNext(); ) {
            String areaAuth = (String) localIterator1.next();
            String[] areaAuthArr = areaAuth.split("-");
            if ("所有".equals(areaAuthArr[0])) {
                result.put("W_BG_D.NAME", "all");
                break;
            }
            if (result.get("W_BG_D.NAME") == null) {
                result.put("W_BG_D.NAME", areaAuth);
            } else {
                result.put("W_BG_D.NAME",result.get("W_BG_D.NAME") + "," + areaAuth);
            }
        }

        List<String> productAuthList = this.oracleDao.getUserProductAuth(userId);
        Iterator it1 = productAuthList.iterator();

        while (it1.hasNext()) {
            String warehouseAuth = (String) it1.next();
            if ("-1".equals(warehouseAuth)) {
                result.put("DM_SL_WAREHOUSE.WH_CODE", "all");
                result.put("DM_SL_WAREHOUSE_D.WH_CODE", "all");
                result.put("W_SL_WAREHOUSE_D.WH_CODE", "all");
                break;
            }

            if (result.get("DM_SL_WAREHOUSE.WH_CODE") == null) {
                result.put("DM_SL_WAREHOUSE.WH_CODE", "'" + warehouseAuth + "'");
            } else {
                result.put("DM_SL_WAREHOUSE.WH_CODE",result.get("DM_SL_WAREHOUSE.WH_CODE") + ",'" + warehouseAuth + "'");
            }

            if (result.get("DM_SL_WAREHOUSE_D.WH_CODE") == null) {
                result.put("DM_SL_WAREHOUSE_D.WH_CODE", "'" + warehouseAuth + "'");
            } else {
                result.put("DM_SL_WAREHOUSE_D.WH_CODE",result.get("DM_SL_WAREHOUSE_D.WH_CODE") + ",'" + warehouseAuth + "'");
            }

            if (result.get("W_SL_WAREHOUSE_D.WH_CODE") == null) {
                result.put("W_SL_WAREHOUSE_D.WH_CODE", "'" + warehouseAuth + "'");
            } else {
                result.put("W_SL_WAREHOUSE_D.WH_CODE", result.get("W_SL_WAREHOUSE_D.WH_CODE") + ",'" + warehouseAuth + "'");
            }
        }

        List<String> requestTypeAuthList = this.oracleDao.getUserRequestTypeAuth(userId);
        Iterator it2 = requestTypeAuthList.iterator();

        while (it2.hasNext()) {
            String requestTypeAuth = (String) it2.next();
            if ("-1".equals(requestTypeAuth)) {
                result.put("DM_SL_REQUEST_TYPE.LOOKUP_CODE", "all");
                result.put("DM_SL_REQUEST_TYPE_D.LOOKUP_CODE", "all");
                result.put("W_SL_REQUEST_TYPE_D.WH_CODE", "all");
                break;
            }

            if (result.get("DM_SL_REQUEST_TYPE.LOOKUP_CODE") == null) {
                result.put("DM_SL_REQUEST_TYPE.LOOKUP_CODE", "'" + requestTypeAuth + "'");
            } else {
                result.put("DM_SL_REQUEST_TYPE.LOOKUP_CODE", result.get("DM_SL_REQUEST_TYPE.LOOKUP_CODE") + ",'" + requestTypeAuth + "'");
            }

            if (result.get("DM_SL_REQUEST_TYPE_D.LOOKUP_CODE") == null) {
                result.put("DM_SL_REQUEST_TYPE_D.LOOKUP_CODE", "'" + requestTypeAuth + "'");
            } else {
                result.put("DM_SL_REQUEST_TYPE_D.LOOKUP_CODE", result.get("DM_SL_REQUEST_TYPE_D.LOOKUP_CODE") + ",'" + requestTypeAuth + "'");
            }

            if (result.get("W_SL_REQUEST_TYPE_D.WH_CODE") == null) {
                result.put("W_SL_REQUEST_TYPE_D.WH_CODE", "'" + requestTypeAuth + "'");
            } else {
                result.put("W_SL_REQUEST_TYPE_D.WH_CODE", result.get("W_SL_REQUEST_TYPE_D.WH_CODE") + ",'" + requestTypeAuth + "'");
            }
        }

        List<String> platfMapperAuthList = this.oracleDao.getUserPlatfMapperAuth(userId);
        log.debug("电商平台权限 通过oracle查询结果为:{}", platfMapperAuthList);
        String[] requestTypeAuth = PJ_EC_SALES_TABLE;


        for (int ii = 0; ii < requestTypeAuth.length; ++ii) {
            String plTableStr = requestTypeAuth[ii];
            Iterator it3 = platfMapperAuthList.iterator();

            while (it3.hasNext()) {
                String platfMapperAuth = (String) it3.next();
                if ("-1".equals(platfMapperAuth) || "所有".equals(platfMapperAuth)) {
                    result.put(plTableStr + ".PLATFORM_SALE_AREA", "all");
                    break;
                }

                String[] sArray = platfMapperAuth.split("-");
                String whereStr = "";
                if (sArray.length > 0 && !"".equals(sArray[0])) {
                    if (!"所有".equals(sArray[0])) {
                        if (result.get(plTableStr + ".PLATFORM_SALE_AREA") == null) {
                            whereStr = "(" + plTableStr + ".PLATFORM_NAME='" + sArray[0] + "'";
                            if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                                whereStr = whereStr + " and " + plTableStr + ".SALE_AREA_NAME='" + sArray[1] + "')";
                            } else {
                                whereStr = whereStr + ")";
                            }
                        } else {
                            whereStr = (String) result.get(plTableStr + ".PLATFORM_SALE_AREA");
                            whereStr = whereStr + " or (" + plTableStr + ".PLATFORM_NAME='" + sArray[0] + "'";
                            if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                                whereStr = whereStr + " and " + plTableStr + ".SALE_AREA_NAME='" + sArray[1] + "')";
                            } else {
                                whereStr = whereStr + ")";
                            }
                        }
                    } else if (result.get(plTableStr + ".PLATFORM_SALE_AREA") == null) {
                        if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                            whereStr = "( " + plTableStr + ".SALE_AREA_NAME='" + sArray[1] + "')";
                        }
                    } else {
                        whereStr =  result.get(plTableStr + ".PLATFORM_SALE_AREA");
                        if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                            whereStr = whereStr + " or ( " + plTableStr + ".SALE_AREA_NAME='" + sArray[1] + "')";
                        }
                    }

                    if (!"".equals(whereStr)) {
                        result.put(plTableStr + ".PLATFORM_SALE_AREA", whereStr);
                    }
                }
            }
        }

        List<String> userCategAuthList = this.oracleDao.getUserCategAuth(userId);
        log.debug("电商品类权限 通过oracle查询结果为:{}", userCategAuthList);

//        String[] var31 = PJ_EC_SALES_TABLE;
        for (int ii = 0; ii < requestTypeAuth.length; ++ii) {
            String plTableStr = requestTypeAuth[ii];
            Iterator it4 = userCategAuthList.iterator();

            while (it4.hasNext()) {
                String userCategAuth = (String) it4.next();
                if ("-1".equals(userCategAuth) || "所有".equals(userCategAuth)) {
                    result.put(plTableStr + ".PRO_CATEG_BRAND", "all");
                    break;
                }

                String[] sArray = userCategAuth.split("-");
                String whereStr = "";
                if (sArray.length > 0 && !"".equals(sArray[0])) {
                    if (!"所有".equals(sArray[0])) {
                        if (result.get(plTableStr + ".PRO_CATEG_BRAND") == null) {
                            whereStr = "(" + plTableStr + ".PRO_CATEG_NAME='" + sArray[0] + "'";
                            if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                                whereStr = whereStr + " and " + plTableStr + ".PRO_BRAND_NAME='" + sArray[1] + "')";
                            } else {
                                whereStr = whereStr + ")";
                            }
                        } else {
                            whereStr = result.get(plTableStr + ".PRO_CATEG_BRAND");
                            whereStr = whereStr + " or (" + plTableStr + ".PRO_CATEG_NAME='" + sArray[0] + "'";
                            if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                                whereStr = whereStr + " and " + plTableStr + ".PRO_BRAND_NAME='" + sArray[1] + "')";
                            } else {
                                whereStr = whereStr + ")";
                            }
                        }
                    } else if (result.get(plTableStr + ".PRO_CATEG_BRAND") == null) {
                        if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                            whereStr = "( " + plTableStr + ".PRO_BRAND_NAME='" + sArray[1] + "')";
                        }
                    } else {
                        whereStr = result.get(plTableStr + ".PRO_CATEG_BRAND");
                        if (sArray.length > 1 && !"".equals(sArray[1]) && !"所有".equals(sArray[1])) {
                            whereStr = whereStr + " or ( " + plTableStr + ".PRO_BRAND_NAME='" + sArray[1] + "')";
                        }
                    }

                    if (!"".equals(whereStr)) {
                        result.put(plTableStr + ".PRO_CATEG_BRAND", whereStr);
                    }
                }
            }
        }
        log.debug("通过oracle查询出来的权限：{}", result.toString());
        return result;
    }

    public String getDataAuthFromRedis(String[] tableName, String yiliUserId) {
        if (StringUtils.isEmpty(yiliUserId)) {
            return " AND W_BG_D.BG_NAME = '-1' ";
        } else {
            String userDataAuth = null;
            try {
                userDataAuth = RedisUtil.getString(yiliUserId + "-authMap");
            } catch (Exception e) {
                log.warn("Redis fail.", e);
            }

            Map yiliDataAuthMap = YiliDataAuthEnum.getYiliDataAuthMap();
            Map authMap;
            try {
                authMap = StringUtils.isNotEmpty(userDataAuth) ? (Map) JSONObject.parseObject(userDataAuth, Map.class) : this.getDataAuthFromOracle(Integer.valueOf(yiliUserId));
            } catch (Exception e) {
                log.warn("Redis fail.", e);
                authMap = this.getDataAuthFromOracle(Integer.valueOf(yiliUserId));
            }

            log.debug("查询出来的权限:{}", authMap);
            List<String> conditionList = new ArrayList();
            String areaTableName = "";
//            String[] var8 = tableName;
//            int var9 = tableName.length;

            String tValue;
            for (int i = 0; i < tableName.length; ++i) {
                String str = tableName[i];
                if (!"W_AREA_D".equals(str) && !"W_AREA_KYLIN_D".equals(str) && !"DM_AREA_KYLIN_D".equals(str)) {
                    if (yiliDataAuthMap.get(str) != null) {
                        String field = (String) yiliDataAuthMap.get(str);
                        tValue = null;
                        try {
                            tValue = (String) authMap.get(field);
                        } catch (Exception e) {
                            log.warn("Redis fail.", e);
                        }
                        if (tValue != null && !"all".equals(tValue)) {
                            conditionList.add(field + " IN (" + tValue + ") ");
                        }
                    }

                    String[] requestTypeAuth = PJ_EC_SALES_TABLE;
//                    int var31 = var29.length;

                    for (int ii = 0; ii <requestTypeAuth.length; ++ii) {
                        String plTableStr = requestTypeAuth[ii];
                        if (plTableStr.equals(str)) {
                            String platformValue = null;
                            try {
                                platformValue = (String) authMap.get(str + ".PLATFORM_SALE_AREA");
                            } catch (Exception e) {
                                log.warn("Redis fail.", e);
                            }
                            if (platformValue != null && !"all".equals(platformValue)) {
                                conditionList.add(" (" + platformValue + ") ");
                            }
                            String categValue = null;
                            try {
                                categValue = (String) authMap.get(str + ".PRO_CATEG_BRAND");
                            } catch (Exception e) {
                                log.warn("Redis fail.", e);
                            }
                            if (categValue != null && !"all".equals(categValue)) {
                                conditionList.add(" (" + categValue + ") ");
                            }
                        }
                    }
                } else {
                    areaTableName = str;
                }
            }

            if (!"".equals(areaTableName)) {
                String areaValue = null;
                try {
                    areaValue = (String) authMap.get("W_BG_D.NAME");
                } catch (Exception e) {
                    log.warn("Redis fail.", e);
                }

                if ("all".equals(areaValue)) {
                    conditionList.add(0, "W_BG_D.BG_NAME IN ('集团','奶粉事业部','酸奶事业部','冷饮事业部','液态奶事业部','健康饮品事业部','奶酪事业部','电商')");
                } else if (StringUtils.isEmpty(areaValue)) {
                    conditionList.add(0, "W_BG_D.BG_NAME = '-1'");
                } else {
                    List<String> tConditionList = new ArrayList();
                    String[] areaValueArray = areaValue.split(",");

                    for (int iii = 0; iii < areaValueArray.length; ++iii) {
                        tValue = areaValueArray[iii];
                        String[] tValueArr = tValue.split("-");
                        if (tValueArr.length >= 2 && !"所有".equals(tValueArr[1])) {
                            StringBuilder builder = new StringBuilder("(");

                            for (int i = 0; i < tValueArr.length; ++i) {
                                switch (i) {
                                    case 0:
                                        if ("电子商务分公司".equals(tValueArr[0])) {
                                            builder.append("W_BG_D.BG_NAME='").append("电商").append("'");
                                        } else {
                                            builder.append("W_BG_D.BG_NAME='").append(tValueArr[0]).append("'");
                                        }
                                        break;
                                    case 1:
                                        builder.append(" AND ").append(areaTableName).append(".BIG_AREA_NAME='").append(tValueArr[1]).append("'");
                                        break;
                                    case 2:
                                        builder.append(" AND ").append(areaTableName).append(".AREA_NAME='").append(tValueArr[2]).append("'");
                                        break;
                                    case 3:
                                        builder.append(" AND ").append(areaTableName).append(".CITIES_NAME='").append(tValueArr[3]).append("'");
                                        break;
                                    case 4:
                                        builder.append(" AND ").append(areaTableName).append(".CITY_NAME='").append(tValueArr[3]).append("'");
                                }
                            }

                            builder.append(")");
                            tConditionList.add(builder.toString());
                        } else if ("电子商务分公司".equals(tValueArr[0])) {
                            tConditionList.add("W_BG_D.BG_NAME='电商'");
                        } else {
                            tConditionList.add("W_BG_D.BG_NAME='" + tValueArr[0] + "'");
                        }
                    }

                    if (tConditionList.size() > 0) {
                        conditionList.add(0, "(" + String.join(" OR ", tConditionList) + ")");
                    } else {
                        conditionList.add(0, "W_BG_D.BG_NAME = '-1'");
                    }
                }
            }

            if (conditionList.size() > 0) {
                log.debug("拼装权限后的list:{}", conditionList.toString());
                return " AND " + String.join(" AND ", conditionList) + " ";
            } else {
                log.debug("拼装权限后的list为空");
                return "";
            }
        }
    }
}
