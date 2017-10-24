package com.hkd.service;

import com.hkd.entity.LoanDetails;
import com.hkd.entity.UserCardInfo;

import java.util.List;

/**
 * Created by 1 on 2017-08-22.
 */
public interface LoanDetailsService {

    /**
     * 查询本期待还数据
     * @param poolId 总订单id
     * @return
     */
    LoanDetails findExpectedRepayment(Integer poolId);


    /**
     * 根据用户订单总id查询用户借款信息
     * @param poolId 用户借款订单总id
     * @return
     */
    List<LoanDetails>findOrderListByPoolId(Integer poolId);

    /**
     * 根据用户id查询用户银行卡信息
     * @param userId 用户id
     * @return
     */
    UserCardInfo findUserCardByUserId(String userId);


}
