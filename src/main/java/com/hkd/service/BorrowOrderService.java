package com.hkd.service;

import com.hkd.entity.AssetBorrowOrder;
import com.hkd.entity.BorrowOrder;

import java.util.Map;

/**
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 13:02
 * Description:
 */

public interface BorrowOrderService {
    /**
     * 检查当前用户是否存在未还款完成的订单
     * @param userId
     * @return 1：是；0：否
     */
    Integer checkBorrow(Integer userId);
    /**
     * 查询当前用户最近一次审核失败的订单
     * @param userId
     * @return
     */
    Map<String,String> findAuditFailureOrderByUserId(String userId);


    /**
     * 根据用户id查询分期订单
     * @param id 用户id
     * @return
     */
    AssetBorrowOrder findBorrowOrderById(Integer id);
    
    /**
	 * 查询用户最近借款信息 任何状态 最新为准
	 * @param parseInt
	 * @return
	 */
	public BorrowOrder selectBorrowOrderNowUseId(Integer userId);
}
