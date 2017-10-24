package com.hkd.mapper;

import com.hkd.entity.User;
import com.hkd.entity.UserCardInfo;
import com.hkd.util.MyMapper;

/**
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 11:52
 * Description:
 */

public interface UserMapper extends MyMapper<User> {


    User searchByUserid(Integer integer);
    
}
