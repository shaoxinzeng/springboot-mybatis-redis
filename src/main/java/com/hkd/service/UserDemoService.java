package com.hkd.service;


import com.github.pagehelper.PageInfo;
import com.hkd.entity.UserDemo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by 1 on 2017-08-15.
 */

public interface UserDemoService {


    /**
     * 添加用户
     * @param userDemo 用户实体
     * @return
     */
    int addUser(UserDemo userDemo);

    /**
     * 根据用户id 删除用户
     * @param id 用户id
     * @return
     */
    int deleteUserById(Integer id);

    /**
     * 修改用户
     * @param userDemo 用户实体
     * @return
     */
    int updateUser(UserDemo userDemo);

    /**
     * 查询用户数据
     * @return
     */
    PageInfo<UserDemo> findUserDemo();

}
