package com.hkd.mapper;

import com.hkd.entity.BackConfigParams;
import com.hkd.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * User: xuedaiyao
 * Date: 2017/8/24
 * Time: 16:42
 * Description:
 */

public interface BackConfigParamsMapper extends MyMapper<BackConfigParams> {

    List<BackConfigParams> getParamsByType(@Param("type") String type);

    BackConfigParams getParamsByKey(@Param("key") String key);

    /**
	 * 
	 * @param params
	 *            sysType参数分类
	 * @return
	 */
	public List<BackConfigParams> findParams(HashMap<String, Object> params);
}

