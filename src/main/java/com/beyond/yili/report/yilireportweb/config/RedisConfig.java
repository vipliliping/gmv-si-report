package com.beyond.yili.report.yilireportweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author vipliliping
 * @create 2019/4/23 10:55
 * @desc
 **/
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private String nodes;
    private String masterName;
    private String password;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int timeout;

    public RedisConfig() {
    }

    public String getNodes() {
        return this.nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getMasterName() {
        return this.masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxTotal() {
        return this.maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String toString() {
        return "RedisConfig{nodes='" + this.nodes + '\'' + ", masterName='" + this.masterName + '\'' + ", password='" + this.password + '\'' + ", maxTotal='" + this.maxTotal + '\'' + ", maxIdle='" + this.maxIdle + '\'' + ", minIdle='" + this.minIdle + '\'' + ", timeout='" + this.timeout + '\'' + '}';
    }
}