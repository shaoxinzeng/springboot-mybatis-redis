package com.hkd.mapper;

import com.hkd.entity.LoanDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 1 on 2017-08-22.
 */
public interface LoanDetailsMapper {


    /**
     * 统计滞纳金
     * @param ids  产生滞纳金的订单id
     * @return
     */
    Integer countLateFeeByOrderId(int[] ids);

    /**
     * 查询本期借款信息
     * @param poolId
     * @return
     */
    LoanDetails findExpectedRepayment(@Param("poolId") Integer poolId);

    /**
     *根据用户订单总id查询用户借款信息
     * @param poolId
     * @return
     */
    List<LoanDetails> findOrderListByPoolId(@Param("poolId") Integer poolId);
}
