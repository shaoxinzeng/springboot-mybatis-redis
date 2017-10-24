package com.hkd.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkd.entity.BackConfigParams;
import com.hkd.service.BackConfigParamsService;

@Component
public class ConfigUtil {
	
	@Autowired
	public static BackConfigParamsService backConfigParamsService;
	
	/**
     * 获取APP的配置参数
     * @param appName
     * @param param
     * @return
     */
    public static String getAppConfig(String appName, String param) {
        if(StringUtils.isBlank(appName)){
            appName = "hkd";
        }
        BackConfigParams backConfigParams = backConfigParamsService.findConfigValue(param+"_"+appName);
        if(backConfigParams!=null) {
        	return backConfigParams.getSysValue();
        }
        return null;
    }
}
