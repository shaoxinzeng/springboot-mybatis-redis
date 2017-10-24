package com.hkd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * User: xuedaiyao
 * Date: 2017/8/23
 * Time: 13:56
 * Description:
 */

@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterProperties {

    //集群节点
    private List<String> nodes=new ArrayList<>();

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}

