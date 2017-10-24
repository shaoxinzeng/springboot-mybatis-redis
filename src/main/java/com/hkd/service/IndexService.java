package com.hkd.service;

import com.hkd.entity.vo.UserRequestVo;

import java.util.List;
import java.util.Map;

/**
 * User: xuedaiyao
 * Date: 2017/8/18
 * Time: 12:31
 * Description:
 */

public interface IndexService {
    /**
     *   首页展示
     * @param userRequestVo
     * @return
     */
    Object show(UserRequestVo userRequestVo);

    /**
     *  查询服务费明细
     * @param periods 借款期数
     * @param moneyAmount  借款金额
     * @return
     */
    List<Map<String, Object>> getServiceFeeDetail(Integer periods, Integer moneyAmount);

    /**
     *  根据期数获取服务费率明细
     * @param periods
     * @return
     */
    Map<String,Double> getAllrateDetail(String periods);
}
