package com.beyond.yili.report.yilireportweb.infra.impl;

import com.beyond.sql.engine.TemplateUtil;
import com.beyond.yili.common.util.FltParaUtil;
import com.beyond.yili.dao.KylinDao;
import com.beyond.yili.report.yilireportweb.infra.YiliKylinFilterInfra;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vipliliping
 * @create 2019/4/23 14:02
 * @desc
 **/
@Service
public class YiliKylinFilterImpl implements YiliKylinFilterInfra {
    private static final Logger log = LoggerFactory.getLogger(YiliKylinFilterImpl.class);
    @Autowired
    private KylinDao kylinDao;

    public YiliKylinFilterImpl() {
    }

    public List<String> queryKylinDataByTemplateForList(Map<String, Object> param, String ftlFileName) throws Exception {
        TemplateUtil templateUtil = new TemplateUtil();
        String sql = templateUtil.generateTemplateSql(ftlFileName, param);
        List<String> result = FltParaUtil.StringToList((List)this.kylinDao.getData(sql).get("data"));
        return result;
    }
}
