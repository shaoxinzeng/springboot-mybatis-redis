package com.hkd.mapper;

import com.hkd.entity.AssetOrderPool;
import com.hkd.util.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface AssetOrderPoolMapper extends MyMapper<AssetOrderPool>{

    int insertOrderSelective(AssetOrderPool assetOrderPool);

}