package com.hkd.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkd.entity.BackConfigParams;
import com.hkd.mapper.BackConfigParamsMapper;
import com.hkd.service.BackConfigParamsService;

@Service
public class BackConfigParamsServiceImpl implements BackConfigParamsService {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	BackConfigParamsMapper backConfigParamsMapper;

	@Override
	public List<BackConfigParams> findParams(HashMap<String, Object> params) {

		return backConfigParamsMapper.findParams(params);
	}

	@Override
	public BackConfigParams findConfigValue(String params) {
		BackConfigParams backConfigParams = backConfigParamsMapper.getParamsByKey(params);
		return backConfigParams;
	}
}
