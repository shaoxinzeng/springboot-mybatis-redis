package com.hkd.controller;

import com.hkd.entity.Result;
import com.hkd.entity.vo.UserRequestVo;
import com.hkd.service.UserLoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 10:59
 * Description: 用户借款controller
*/

@RestController
@RequestMapping("loan")
@Api(value = "用户借款接口",description = "用户借款接口")
public class UserLoanController  {

    @Autowired
    private UserLoanService userLoanService;


    /**
     *  去借款
     * @param userRequestVo 用户登录参数vo
     * @param money 借款金额
     * @param periods 借款期数
     * @return
     */
    @GetMapping("get-confirm-loan")
    @ApiOperation(value = "去借款@xuedaiyao",notes = "去借款-生成借款数据")
    public Result getconfirmData(UserRequestVo userRequestVo, @ApiParam(value = "借款金额", required = true) @RequestParam String money,
                              @ApiParam(value = "借款期数", required = true) @RequestParam Integer periods, HttpServletRequest request) {
       return userLoanService.getconfirmData(userRequestVo,money,periods,request);
    }

    @PostMapping("commit_loan")
    @ApiOperation(value = "提交借款申请@xuedaiyao",notes = "提交借款申请返回消息和订单ID")
    public Result commitLoan(UserRequestVo userRequestVo,
                             @ApiParam(value = "借款金额", required = true) @RequestParam String money,
                             @ApiParam(value = "借款期数", required = true) @RequestParam Integer periods,
                             @ApiParam(value = "支付密码", required = true) @RequestParam String pay_password,
                             HttpServletRequest request){
        return userLoanService.commitLoan(userRequestVo,money,periods,pay_password,request);
    }

}

