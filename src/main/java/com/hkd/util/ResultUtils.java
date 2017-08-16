package com.hkd.util;

import com.hkd.entity.Result;

/**
 * Created by 1 on 2017-08-16.
 */
public class ResultUtils {

    /**
     * 返回成功消息
     *
     * @param object 传入的对象
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCod(200);
        result.setMessage("成功");
        result.setData(object);
        return result;
    }

    /**
     * 成功不返回信息
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 自定义成功消息
     *
     * @param msg    返回消息
     * @param object 返回数据
     * @return
     */
    public static Result customSuccess(String msg, Object object) {
        Result result = new Result();
        result.setCod(200);
        result.setMessage(msg);
        result.setData(object);
        return result;
    }

    /**
     * 返回失败消息
     *
     * @param cod 传入的编码
     * @param msg 信息
     * @return
     */
    public static Result error(Integer cod, String msg) {
        Result result = new Result();
        result.setCod(cod);
        result.setMessage(msg);
        return result;
    }
}
