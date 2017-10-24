package com.hkd.entity.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * User: xuedaiyao
 * Date: 2017/8/23
 * Time: 9:32
 * Description: 用户登录请求Vo
 */

public class UserRequestVo  {

    @ApiModelProperty(value = "设备ID")
    private String deviceId;
    @ApiModelProperty(value = "手机号码")
    private String mobilePhone;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}

