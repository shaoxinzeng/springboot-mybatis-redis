package com.hkd.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hkd.entity.*;
import com.hkd.service.AssetOrderPoolService;
import com.hkd.service.BorrowOrderService;
import com.hkd.service.LoanDetailsService;
import com.hkd.util.PageInfoApp;
import com.hkd.util.ResultUtils;
import com.mysql.jdbc.util.ResultSetUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 2017-08-21.
 */

@RestController
@RequestMapping("/loan")
@Api(value ="用户借款信息", description = "用户借款信息")
public class AssetOrderController {

    private static Logger logger = LoggerFactory.getLogger(AssetOrderController.class);

    @Autowired
    private AssetOrderPoolService assetOrderPoolService;

    @Autowired
    private LoanDetailsService loanDetailsService;

    @Autowired
    private BorrowOrderService borrowOrderService;


    /**
     * 根据用户id查询用户借款列表
     * @param userId 用户id
     * @param pageNum 当前页数
     * @param pageSize 每页多少条
     * @param deviceId 设备号
     * @param mobilePhone 手机号码
     * @return 根据用户分页查询用户借款列表
     */
    @GetMapping("/AssetOrder")
    @ApiOperation(value = "根据用户id查询用户借款列表@shaoxin" , notes = "查询用户借款列表")
    public Result<PageInfo<AssetOrderPool>> findAssetOrderByUserId(@RequestParam String userId,Integer pageNum,Integer pageSize,String deviceId,String mobilePhone){
        PageInfo<Map<String, Object>> pageInfo = assetOrderPoolService.findAssetOrderByUserId(userId,pageNum,pageSize);
        return ResultUtils.success( new PageInfoApp<Map<String, Object>>(pageInfo));
    }


    /**
     *根据总订单id查询用户借款详细订单
     * @param poolId 总的订单id
     * @param deviceId 设备号
     * @param mobilePhone 手机号码
     * @return
     */
    @GetMapping("orderListDetail")
    @ApiOperation(value = "查询用户借款列表详情@shaoxin" , notes = "查询用户借款列表详情")
    public Result<List<LoanDetails>>findOrderListByPoolId(@ApiParam(value = "总订单id", required = true) @RequestParam Integer poolId,String deviceId,String mobilePhone){
        List<LoanDetails> list = loanDetailsService.findOrderListByPoolId(poolId);
        if(list.size()<=0 || null == list){
            logger.info("查询用户借款列表详情异常,暂时没有该用户的总订单;传入总订单id为："+ poolId);
            return ResultUtils.error("-1","暂时没有该用户借款列表详情，请先去借款");
        }
        double moneyAmountSum = Double.parseDouble(list.get(0).getMoneyAmountSum());
        double lateFeeSum = Double.parseDouble(list.get(0).getLateFeeSum());;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("moneyAmountSum", new DecimalFormat("0.00").format(moneyAmountSum/100));
        jsonObject.put("lateFeeSum",new DecimalFormat("0.00").format(lateFeeSum));
        jsonObject.put("allStatus",list.get(0).getAllStatus());
        jsonObject.put("contractName","合同");
        if(StringUtils.isNotBlank(list.get(0).getContractImg())){
            jsonObject.put("contractImg",list.get(0).getContractImg());
        }
        jsonObject.put("item",list);
        return ResultUtils.success(jsonObject);
    }


    /**
     * 还款页面跳转接口
     * @param orderId 详细订单的id
     * @return
     */
//    @GetMapping("repayChoose")
//    @ApiOperation(value="还款页面跳转@shaoxin" , notes = "还款页面跳转")
//    public Result<JSONObject> repayChoose(Integer orderId){
//        AssetBorrowOrder assetBorrowOrder = borrowOrderService.findBorrowOrderById(orderId);
//        String userId = assetBorrowOrder.getUserId().toString();
//        UserCardInfo userCardInfo = loanDetailsService.findUserCardByUserId(userId);
//        String cardNo = userCardInfo.getCard_no().substring(userCardInfo.getCard_no().length() - 4);
//        String backName = userCardInfo.getBankName();
//        String id = orderId.toString();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("cardNo",cardNo);
//        jsonObject.put("backName",backName);
//        jsonObject.put("id",id);
//        return ResultUtils.success(jsonObject);
//    }

}
