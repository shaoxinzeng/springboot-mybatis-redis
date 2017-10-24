package com.hkd.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hkd.entity.AssetBorrowOrder;
import com.hkd.entity.AssetOrderConnect;
import com.hkd.entity.AssetOrderPool;
import com.hkd.mapper.AssetBorrowOrderMapper;
import com.hkd.mapper.AssetOrderConnectMapper;
import com.hkd.mapper.AssetOrderPoolMapper;
import com.hkd.service.AssetOrderPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hkd.entity.AssetOrderPool.borrowStatusMap_front;

/**
 * Created by 1 on 2017-08-21.
 */
@Service
public class AssetOrderPoolServiceImpl implements AssetOrderPoolService{

    @Autowired
    private AssetOrderPoolMapper assetOrderPoolMapper;

    @Autowired
    private AssetBorrowOrderMapper assetBorrowOrderMapper;

    @Autowired
    private AssetOrderConnectMapper assetOrderConnectMapper;

    @Override
    public PageInfo<Map<String, Object>> findAssetOrderByUserId(String userId,Integer pageNum,Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        Example example = new Example(AssetOrderPool.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<AssetOrderPool> list = assetOrderPoolMapper.selectByExample(example);
       List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataMap = null;
        for(AssetOrderPool assetOrderPool : list){
            String statusName = borrowStatusMap_front.get(assetOrderPool.getStatus().intValue());
            dataMap = new HashMap<String, Object>();
            dataMap.put("poolId", assetOrderPool.getId().toString());
            dataMap.put("text", "<font color='#ff8003' size='3'>" + statusName + "</font>");
            dataMap.put("time", sdf.format(assetOrderPool.getCreatedAt()));
            dataMap.put("title", "借款" + new DecimalFormat("0.00").format(assetOrderPool.getMoneyAmount()/100)+"元");
            dataMap.put("link_url","/repayment/repay-type" );
            dataList.add(dataMap);
        }


        return new PageInfo<>(dataList);
    }

    @Override
    public List<AssetBorrowOrder> findUserLoanRecord(Integer poolId) {
        List<AssetBorrowOrder> list = assetBorrowOrderMapper.selectUserLoanRecord(poolId);
        return list;
    }

    @Override
    public int[] findOrderDetailsByPoolId(Integer poolId) {
        Example example = new Example(AssetOrderConnect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",poolId);
        List<AssetOrderConnect> list = assetOrderConnectMapper.selectByExample(example);
        int [] t = null;
        int []ids = new int[list.size()];
        for(int i =0; i<list.size();i++){
            Integer assetOrderId = list.get(i).getAssetOrderId();
            ids[i] = assetOrderId;
        }
        return ids;
    }
}
