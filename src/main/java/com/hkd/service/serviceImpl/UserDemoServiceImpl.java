package com.hkd.service.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.hkd.entity.UserDemo;
import com.hkd.exception.HkdException;
import com.hkd.mapper.UserDemoMapper;
import com.hkd.service.UserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 1 on 2017-08-15.
 */
@Service
public class UserDemoServiceImpl implements UserDemoService {

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

    @Transactional
    @Override
    public Integer addTest(UserDemo userDemo) throws  Exception{
            UserDemo userDemo1 = new UserDemo();
            userDemo1.setAge(18);
            userDemo.setName("测试1");
            userDemo.setRank("测试1");
            userDemoMapper.insert(userDemo1);
            this.addUser(userDemo1);
            if(true){
                throw new HkdException(-45, "123456");
            }
            int count = userDemoMapper.insert(userDemo);
        return count;
    }

}
