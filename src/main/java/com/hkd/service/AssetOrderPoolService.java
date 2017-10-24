package com.hkd.service;

import com.github.pagehelper.PageInfo;
import com.hkd.entity.AssetBorrowOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 2017-08-21.
 */
public interface AssetOrderPoolService {

    /**
     * 用户借款列表
     * @param userId 用户id
     * @param pageNum 当前页数
     * @param pageSize 每页多少条
     * @return 借款列表
     */
    PageInfo<Map<String, Object>> findAssetOrderByUserId(String userId, Integer pageNum, Integer pageSize);

    /**
     * 根据总订单id查询用户借款详细订单
     * @param poolId 总的订单id
     * @return
     */
    List<AssetBorrowOrder> findUserLoanRecord(Integer poolId);


    /**
     * 根据总的订单id查询详细的订单id数组
     * @param poolId
     * @return
     */
    int[] findOrderDetailsByPoolId(Integer poolId);

}
