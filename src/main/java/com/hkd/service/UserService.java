package com.hkd.service;

import com.hkd.entity.User;
import com.hkd.entity.UserCardInfo;

public interface UserService {

	/**
	 * 根据ID查询用户
	 * @return
	 */
	public User searchByUserid(String userId);
	
	/**
	 * 查询用户银行卡信息
	 * @param id
	 * @return
	 */
	public UserCardInfo findUserBankCard(Integer id);
}
