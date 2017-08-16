package com.hkd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by 1 on 2017-08-16.
 */
@Component
public class RedisLock {

    /**
     * resource: http://www.cnblogs.com/0201zcr/p/5942748.html
     */

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * Lock key path.
     */
//    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
//    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
//    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;

    /**
     * @return lock key
     */
//    public String getLockKey() {
//        return lockKey;
//    }

    public String get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Object result = operations.get(key);
        return null == result ? null : (String)result;
    }

    /**
     * 写入缓存，如果不存在才写入，用于锁，同时设置过期时间
     */
    private boolean setNX(final String key, Object value, long expireTime) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Boolean result = operations.setIfAbsent(key, value);
            //毫秒
            redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
            return null == result ? false : result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新缓存的值，并放回原来的值，同时设置过期时间
     */
    private String getSet(final String key, final String value) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Object result = operations.getAndSet(key, value);
            return null == result ? null : (String)result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得 lock.
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean lock(String lockKey, long expireMsecs, int timeoutMsecs) throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (this.setNX(lockKey, expiresStr, expireMsecs)) {
                // lock acquired
                return true;
            }

            String currentValueStr = this.get(lockKey); //redis里的时间
            //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
            // lock is expired
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {

                // FIXME: 2017/8/1 如果请求很密集，造成很长一段时间内，无限的更新锁的过期时间，该如何处理？
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                String oldValueStr = this.getSet(lockKey, expiresStr);
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受

                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    //只在获得锁的线程里更新过期时间
                    redisTemplate.expire(lockKey, expireMsecs, TimeUnit.MILLISECONDS);
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;

            /*
                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);

        }
        return false;
    }

    /**
     * 根据key判断是否存在锁
     * @param lockKey
     * @return
     */
    public boolean isLocked (String lockKey) {
        /**
         * 只根据key判断当前锁是否存在，但锁有可能不是自己的
         */
        String currentValueStr = this.get(lockKey);
        return null == currentValueStr ? false : true;
    }

    /**
     * Acqurired lock release.
     */
    public synchronized void unlock(String lockKey) {
        if (isLocked(lockKey)) {
            redisTemplate.delete(lockKey);
        }
    }

}