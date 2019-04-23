package com.beyond.yili.report.yilireportweb.config;

import com.beyond.yili.report.yilireportweb.interceptor.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author vipliliping
 * @create 2019/4/23 10:50
 * @desc
 **/
@Configuration
public class MvcWebConfig implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(MvcWebConfig.class);

    public MvcWebConfig() {
    }

    @Bean
    public AuthInterceptor authViewInterceptor() {
        return new AuthInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authViewInterceptor()).excludePathPatterns(new String[]{"/static/*"}).excludePathPatterns(new String[]{"/error"}).addPathPatterns(new String[]{"/**"});
    }
}
