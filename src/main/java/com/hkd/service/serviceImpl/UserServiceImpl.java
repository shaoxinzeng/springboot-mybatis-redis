package com.hkd.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkd.entity.User;
import com.hkd.entity.UserCardInfo;
import com.hkd.mapper.UserCardInfoMapper;
import com.hkd.mapper.UserMapper;
import com.hkd.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserCardInfoMapper userCardInfoMapper;
	
	@Override
	public User searchByUserid(String userId) {
		User user = userMapper.searchByUserid(Integer.parseInt(userId));
		return user;
	}

	@Override
	public UserCardInfo findUserBankCard(Integer id) {
		return userCardInfoMapper.findUserBankCard(id);
	}
}
