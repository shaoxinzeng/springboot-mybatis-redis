package com.hkd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * Created by 1 on 2017-08-23.
 */
@Component
public class RedisUtil {

    private static final Logger LOGGER    = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private JedisCluster jedisCluster;


    /**
     * 设置缓存
     * @param key    缓存key
     * @param value  缓存value
     */
    public void set( String key, String value) {
        jedisCluster.set( key, value);
        LOGGER.debug("RedisUtil:set cache key={},value={}",  key, value);
    }
    /**
     * 设置缓存
     * @param key    缓存key
     * @param value  缓存value
     */
    public void set( byte[]  key, byte[] value) {
        jedisCluster.set( key, value);
        LOGGER.debug("RedisUtil:set cache key={},value={}",  key, value);
    }

    /**
     * 设置缓存，并且自己指定过期时间
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public void setWithExpireTime( String key, String value, int expireTime) {
        jedisCluster.setex( key, expireTime, value);
        LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}",  key, value,
                expireTime);
    }


    /**
     * 获取指定key的缓存
     * @param key
     */
    public String get(String key) {
        String value = jedisCluster.get(key);
        LOGGER.debug("RedisUtil:get cache key={},value={}", key, value);
        return value;
    }



    public byte[] get(byte[] key) {
        byte[] value = jedisCluster.get(key);
        LOGGER.debug("RedisUtil:get cache key={},value={}", key, value);
        return value;
    }

    /**
     * 删除指定key的缓存
     * @param key
     */
    public void deleteWithPrefix( String key) {
        jedisCluster.del( key);
        LOGGER.debug("RedisUtil:delete cache key={}",  key);
    }

    public void delete(String key) {
        jedisCluster.del(key);
        LOGGER.debug("RedisUtil:delete cache key={}", key);
    }
}
