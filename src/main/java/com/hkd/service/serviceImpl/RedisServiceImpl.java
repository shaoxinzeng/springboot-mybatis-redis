package com.hkd.service.serviceImpl;

import com.hkd.entity.User;
import com.hkd.exception.HkdException;
import com.hkd.service.RedisService;
import com.hkd.service.UserService;
import com.hkd.util.RedisUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 2017-08-18.
 */
@Service
public class RedisServiceImpl implements RedisService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String LOGING_DEVICE_PREFIX = "user_";

    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
	private RedisTemplate redisTemplate;
    
	@Autowired 
	private UserService userService;

    @Override
    public String getLoginUser(String deviceId, String telephone){
        String userInfo = null;
        if (null != deviceId && "" != deviceId) {
            if (null != telephone && "" != telephone) {
                userInfo = redisUtil.get(LOGING_DEVICE_PREFIX + deviceId + telephone);
            } else {
                userInfo = redisUtil.get(LOGING_DEVICE_PREFIX + deviceId);
            }
        }
        if (null == userInfo || (null == telephone)|| null == deviceId) {
            throw new HkdException(-2, "登录状态失效");
        }
        logger.info("loginFrontUserByDeiceId2 key :" + (LOGING_DEVICE_PREFIX + deviceId + telephone) + ",userId:" + userInfo);
        return userInfo;
    }
    
}
