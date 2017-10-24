package com.hkd.mapper;

import com.hkd.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: xuedaiyao
 * Date: 2017/8/18
 * Time: 16:45
 * Description:
 */

public interface IndexMapper {

    InfoIndex searchInfoIndex(HashMap<String, Object> map);

    List<InfoNotice> searchInfoNoticeByIndex(HashMap<String, Object> map);

    List<InfoImage> searchInfoImage(HashMap<String, Object> map);

    InfoIndexInfo searchInfoIndexInfo(HashMap<String, Object> map);

    User searchUserByIndex(HashMap<String, Object> map);

    UserCardInfo searchUserCardInfo(HashMap<String, Object> bankMap);

    void updateIndexInfoByUserId(InfoIndexInfo indexInfo);

    AssetOrderPool searchBorrowOrderByIndex(HashMap<String, Object> orderMap);

    List<Repayment> findRepaymentList(Map<String, Object> mapT);

    AssetBorrowOrder findAuditFailureOrderByUserId(HashMap<String, Object> params);

    AssetOrderPool selectBorrowOrderNowUseId(Integer integer);

    List<BackConfigParams> findParams(HashMap<String, Object> params);
}

