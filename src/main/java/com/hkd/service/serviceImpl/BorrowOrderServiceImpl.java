package com.hkd.service.serviceImpl;

import com.hkd.entity.*;
import com.hkd.mapper.BackConfigParamsMapper;
import com.hkd.mapper.BorrowOrderMapper;
import com.hkd.service.BorrowOrderService;
import com.hkd.util.CollectionUtil;
import com.hkd.util.SysCacheUtils;
import com.xiaoleilu.hutool.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 13:04
 * Description:
 */
@Service
public class BorrowOrderServiceImpl  implements BorrowOrderService{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BorrowOrderMapper borrowOrderMapper;
    @Autowired
    private BackConfigParamsMapper backConfigParamsMapper;

    /**
     * 检查当前用户是否存在未还款完成的订单
     *
     * @param userId
     * @return 1：是；0：否
     */
    @Override
    public Integer checkBorrow(Integer userId) {
        Integer result = 0;
        Example example = new Example(AssetBorrowOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andIn("status", Arrays.asList(AssetOrderPool.STATUS_DCS,
                // BorrowOrder.STATUS_JSJJ,
                AssetOrderPool.STATUS_CSTG, AssetOrderPool.STATUS_FSTG, AssetOrderPool.STATUS_HKZ, AssetOrderPool.STATUS_FKZ, AssetOrderPool.STATUS_FKSB,
                AssetOrderPool.STATUS_BFHK, AssetOrderPool.STATUS_YYQ, AssetOrderPool.STATUS_YHZ));
        List<AssetBorrowOrder> normOrders = borrowOrderMapper.selectByExample(example);
        if (CollectionUtil.notEmpty(normOrders)) {
            result = 1;
        }
        return result;
    }

    /**
     * 查询当前用户最近一次审核失败的订单
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, String> findAuditFailureOrderByUserId(String userId) {
        Map<String, String> result = new HashMap<String, String>();
        Integer code = 0;
        String msg = "";
        int nextLoanDay = 0;// 剩余可借款天数
        int interval_day = 0; // 申请失败距当前时间的间隔天数
        BackConfigParams backConfigParams = backConfigParamsMapper.getParamsByKey("INTERVAL_BORROW_AGAIN");
        int interval = Integer.valueOf(backConfigParams.getSysValue());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("statusList", Arrays.asList(AssetOrderPool.STATUS_CSBH, AssetOrderPool.STATUS_FSBH, AssetOrderPool.STATUS_FKBH));
        AssetBorrowOrder bo = borrowOrderMapper.findAuditFailureOrderByUserId(params);
        if (bo != null) {
            Date date = new Date();
            Date smDate = new Date();
            try {
                if (bo.getVerifyLoanTime() != null) {
                    smDate = bo.getVerifyLoanTime();
                } else if (bo.getVerifyReviewTime() != null) {
                    smDate = DateUtil.parseDate(bo.getVerifyReviewTime()) ;
                } else if (bo.getVerifyTrialTime() != null) {
                    smDate = bo.getVerifyTrialTime();
                }
                interval_day = (int)DateUtil.betweenDay(smDate, date,true);
                code = interval_day < interval ? -1 : 0;

            } catch (Exception e) {
                code = -1;
            }
            if (code == -1) {
                msg = "距离上次审核失败不足" + interval + "天，请" + (interval - interval_day) + "天后重新申请。";
                nextLoanDay = (interval - interval_day);
                result.put("canLoan", new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.offsetDay(smDate, interval-1 )));
            }
        }
        result.put("code", code + "");
        result.put("msg", msg);
        result.put("nextLoanDay", String.valueOf(nextLoanDay));
        return result;
    }



    @Override
    public AssetBorrowOrder findBorrowOrderById(Integer id) {
        AssetBorrowOrder assetBorrowOrder = borrowOrderMapper.selectByPrimaryKey(id);
        return assetBorrowOrder;
    }
    
    @Override
	public BorrowOrder selectBorrowOrderNowUseId(Integer userId) {
		return borrowOrderMapper.selectBorrowOrderNowUseId(userId);
	}
}

