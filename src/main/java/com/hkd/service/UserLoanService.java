package com.hkd.service;

import com.hkd.entity.Result;
import com.hkd.entity.vo.UserRequestVo;

import javax.servlet.http.HttpServletRequest;

/**
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 15:31
 * Description:
 */

public interface UserLoanService {
    /**
     *  去借款
     * @param userRequestVo 用户登录参数vo
     * @param money 借款金额
     * @param periods 借款期数
     * @return
     */
    Result getconfirmData(UserRequestVo userRequestVo, String money, Integer periods, HttpServletRequest request);
    /**
     *  提交借款申请
     * @param userRequestVo 用户请求VO
     * @param money 借款金额
     * @param periods 借款期数
     * @param pay_password 付款密码
     * @param request
     * @return
     */
    Result commitLoan(UserRequestVo userRequestVo,String money,Integer periods,String pay_password,HttpServletRequest request);
}
