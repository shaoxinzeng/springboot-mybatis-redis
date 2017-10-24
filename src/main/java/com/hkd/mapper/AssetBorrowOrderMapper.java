package com.hkd.mapper;

import com.hkd.entity.AssetBorrowOrder;
import com.hkd.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssetBorrowOrderMapper extends MyMapper<AssetBorrowOrder>{

    List<AssetBorrowOrder> selectUserLoanRecord(@Param("poolId") Integer poolId);
}