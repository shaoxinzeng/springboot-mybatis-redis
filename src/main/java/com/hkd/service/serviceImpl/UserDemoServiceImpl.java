package com.hkd.service.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.hkd.entity.UserDemo;
import com.hkd.mapper.UserDemoMapper;
import com.hkd.service.UserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 1 on 2017-08-15.
 */
@Service
public class UserDemoServiceImpl implements UserDemoService{

    @Autowired
    private UserDemoMapper userDemoMapper;

    @Override
    public int addUser(UserDemo userDemo) {
        int count = userDemoMapper.insert(userDemo);
        return count;
    }

    @Override
    public int deleteUserById(Integer id) {
        int count = userDemoMapper.deleteByPrimaryKey(id);
        return count;
    }

    @Override
    public int updateUser(UserDemo userDemo) {
        int count = userDemoMapper.updateByPrimaryKeySelective(userDemo);
        return count;
    }

    @Override
    public PageInfo<UserDemo> findUserDemo() {
        List<UserDemo> list = userDemoMapper.selectAll();
        return new PageInfo<>(list);
    }

}
