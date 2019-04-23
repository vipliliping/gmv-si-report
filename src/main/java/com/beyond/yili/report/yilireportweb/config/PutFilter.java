package com.beyond.yili.report.yilireportweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;

/**
 * @author vipliliping
 * @create 2019/4/23 10:54
 * @desc
 **/
@Configuration
public class PutFilter extends HttpPutFormContentFilter {
    public PutFilter() {
    }
}