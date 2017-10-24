package com.hkd.mapper;

import com.hkd.entity.AssetBorrowOrder;
import com.hkd.entity.BorrowOrder;
import com.hkd.entity.RiskCreditUser;
import com.hkd.util.MyMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: xuedaiyao
 * Date: 2017/8/22
 * Time: 13:19
 * Description:
 */

public interface BorrowOrderMapper extends MyMapper<AssetBorrowOrder> {

    AssetBorrowOrder findAuditFailureOrderByUserId(HashMap<String, Object> params);

    void updateInfoUserInfoBorrowStatus(HashMap<String, Object> borrowMap);

    List<AssetBorrowOrder> findByUserId(Integer integer);

    Map<String,BigDecimal> countAddAmount(int i);

    void insertRiskUser(RiskCreditUser risk);
    
    BorrowOrder selectBorrowOrderNowUseId(Integer userId);
}

