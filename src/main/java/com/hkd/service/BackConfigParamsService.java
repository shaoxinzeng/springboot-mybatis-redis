package com.hkd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hkd.entity.BackConfigParams;

public interface BackConfigParamsService {
	/**
	 * 
	 * @param params
	 *            sysType参数分类ASSETS_TYPE是资产类型
	 * @return
	 */
	public List<BackConfigParams> findParams(HashMap<String, Object> params);

	public BackConfigParams findConfigValue(String appName); 
}
