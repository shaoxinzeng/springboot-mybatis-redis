package com.hkd.entity;

/**
 * Created by 1 on 2017-08-16.
 */
public class Result<T> {
    /**
     * 结果码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 具体内容
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
