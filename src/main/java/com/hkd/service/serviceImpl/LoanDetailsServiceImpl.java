package com.hkd.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.hkd.entity.*;
import com.hkd.mapper.AssetOrderConnectMapper;
import com.hkd.mapper.AssetOrderPoolMapper;
import com.hkd.mapper.LoanDetailsMapper;
import com.hkd.mapper.UserCardInfoMapper;
import com.hkd.service.AssetOrderPoolService;
import com.hkd.service.LoanDetailsService;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import tk.mybatis.mapper.entity.Example;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.hkd.entity.AssetOrderPool.borrowStatusMap_front;

/**
 * Created by 1 on 2017-08-22.
 */
@Service
public class LoanDetailsServiceImpl implements LoanDetailsService{

    @Autowired
    private LoanDetailsMapper loanDetailsMapper;

    @Autowired
    private AssetOrderPoolService assetOrderPoolService;

    @Autowired
    private AssetOrderPoolMapper assetOrderPoolMapper;

    @Autowired
    private UserCardInfoMapper userCardInfoMapper;


    @Override
    public LoanDetails findExpectedRepayment(Integer poolId) {
        int []ids = assetOrderPoolService.findOrderDetailsByPoolId(poolId);
        //滞纳金
        Integer LateFee = loanDetailsMapper.countLateFeeByOrderId(ids);

        Example example = new Example(AssetOrderPool.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",poolId);
        AssetOrderPool assetOrderPool = assetOrderPoolMapper.selectByExample(example).get(0);
        //借款总金额
        Integer moneyAmountSum = assetOrderPool.getMoneyAmount();

        LoanDetails loanDetails =  loanDetailsMapper.findExpectedRepayment(poolId);
        //本期待还金额
        //Integer moneyAmount = loanDetails.getMoneyAmount();
        //本期应还日期
        String loanEndTime = loanDetails.getLoanEndTime();

       // loanDetails.setLateFee(LateFee);

        //loanDetails.setMoneyAmountSum(moneyAmountSum);

        return loanDetails;
    }

    @Override
    public List<LoanDetails> findOrderListByPoolId(Integer poolId) {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       List<LoanDetails>  list = loanDetailsMapper.findOrderListByPoolId(poolId);
       int []ids = assetOrderPoolService.findOrderDetailsByPoolId(poolId);
       double LateFeeSum = 0.00;
       if(null != ids && ids.length>0){
           LateFeeSum = loanDetailsMapper.countLateFeeByOrderId(ids);
       }

        //每一期的还款金额
       double moneyAmount =0.00;
       for(LoanDetails loanDetails : list){
           moneyAmount =  Double.parseDouble(loanDetails.getMoneyAmount());
           String loanEndTime = loanDetails.getLoanEndTime();
           try {
               Date d=sdf.parse(loanEndTime);
               loanEndTime = sdf.format(d);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           loanDetails.setLoanEndTime(loanEndTime);
           loanDetails.setMoneyAmount(new DecimalFormat("0.00").format(moneyAmount/100));
           loanDetails.setLateFee(new DecimalFormat("0.00").format(Double.parseDouble(loanDetails.getLateFee())/100));
           loanDetails.setLateFeeSum(new DecimalFormat("0.00").format(LateFeeSum/100));
       }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("item",list);
        return list;
    }

    @Override
    public UserCardInfo findUserCardByUserId(String userId) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("userId",userId);
        List<UserCardInfo> userCardInfoList = userCardInfoMapper.findUserCardByUserId(userId,null,null);
        if(null != userCardInfoList && userCardInfoList.size()>0){
            return userCardInfoList.get(0);
        }
        return null;
    }
}
