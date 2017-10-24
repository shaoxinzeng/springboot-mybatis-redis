package com.hkd.service;

import com.hkd.entity.User;

/**
 * Created by 1 on 2017-08-18.
 */
public interface RedisService {

    String getLoginUser(String deviceId, String telephone) ;
}
