package com.hkd.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: xuedaiyao
 * Date: 2017/8/23
 * Time: 13:59
 * Description:
 */

@Configuration
@ConditionalOnClass(RedisClusterConfig.class)
@EnableConfigurationProperties(RedisClusterProperties.class)
public class RedisClusterConfig {

    @Resource
    private RedisClusterProperties redisClusterProperties;

    @Value("${spring.redis.password:#{null}}")
    private String redispassword;

    @Bean
    public JedisCluster redisCluster(){

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(100);
        genericObjectPoolConfig.setMaxTotal(3000);
        genericObjectPoolConfig.setMaxWaitMillis(3000);
        genericObjectPoolConfig.setMinIdle(10);

        Set<HostAndPort> nodes = new HashSet<>();
        for (String node:redisClusterProperties.getNodes()){
            String[] parts= StringUtils.split(node,":");
            Assert.state(parts.length==2, "redis node shoule be defined as 'host:port', not '" + Arrays.toString(parts) + "'");
            nodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1])));
        }
        if (null == redispassword){
            return new JedisCluster(nodes);
        }
        return new JedisCluster(nodes, 3000, 3000,5,redispassword,genericObjectPoolConfig);
    }






}

