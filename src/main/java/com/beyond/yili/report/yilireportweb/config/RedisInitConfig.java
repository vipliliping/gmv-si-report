package com.beyond.yili.report.yilireportweb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vipliliping
 * @create 2019/4/23 11:25
 * @desc
 **/
@Configuration
public class RedisInitConfig {
    private static final Logger log = LoggerFactory.getLogger(RedisInitConfig.class);
    @Autowired
    private RedisConfig redisConfig;

    @Bean({"jedisPoolConfig"})
    public JedisPoolConfig jedisPoolConfig() {
        log.info("JedisPool initialize start ...");
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(this.redisConfig.getMaxTotal());

        config.setMaxIdle(this.redisConfig.getMaxIdle());

        config.setMinIdle(this.redisConfig.getMinIdle());

        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        log.info("JedisPool initialize end ...");
        return config;
    }

    @Bean({"sentinelPool"})
    public JedisSentinelPool initJedisPool(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
        Set<String> nodeSet = new HashSet();

        String nodeString = this.redisConfig.getNodes();
        if ((nodeString == null) || ("".equals(nodeString))) {
            log.error("RedisSentinelConfiguration initialize error nodeString is null");
            throw new RuntimeException("RedisSentinelConfiguration initialize error nodeString is null");
        }
        String[] nodeArray = nodeString.split(",");
        if ((nodeArray == null) || (nodeArray.length == 0)) {
            log.error("RedisSentinelConfiguration initialize error nodeArray is null");
            throw new RuntimeException("RedisSentinelConfiguration initialize error nodeArray is null");
        }
        for (String node : nodeArray) {
            log.info("Read node : {}��", node);
            nodeSet.add(node);
        }
        JedisSentinelPool jedisPool = null;
        try {
            if ((this.redisConfig.getPassword() == null) || ("".equals(this.redisConfig.getPassword().trim()))) {
                jedisPool = new JedisSentinelPool(this.redisConfig.getMasterName(), nodeSet, jedisPoolConfig, this.redisConfig.getTimeout());
            } else {
                jedisPool = new JedisSentinelPool(this.redisConfig.getMasterName(), nodeSet, jedisPoolConfig, this.redisConfig.getTimeout(), this.redisConfig.getPassword());
            }
        } catch (Exception e) {
            log.error("Jedis pool init failed.");
            e.printStackTrace();
        }
        return jedisPool;
    }
}

