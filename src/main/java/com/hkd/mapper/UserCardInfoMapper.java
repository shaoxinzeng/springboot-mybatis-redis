package com.hkd.mapper;

import com.hkd.entity.UserCardInfo;
import com.hkd.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 15:04
 * Description:
 */

public interface UserCardInfoMapper extends MyMapper<UserCardInfo> {

    List<UserCardInfo> findUserCardByUserId(@Param("userId") String userId, @Param("type") Integer type, @Param("status") Integer status);

    Map<String,String> selectBankInfoByBankId(Integer bank_id);
    
    /**
	 * 查询用户银行卡信息
	 * @param id
	 * @return
	 */
	public UserCardInfo findUserBankCard(Integer id);
}
