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
        result.setCode("0");
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
     * @param code   状态码
     * @param msg    返回消息
     * @return
     */
    public static Result messages(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
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
        result.setCode("0");
        result.setMessage(msg);
        result.setData(object);
        return result;
    }

    /**
     * 返回失败消息
     *
     * @param code 传入的编码
     * @param msg 信息
     * @return
     */
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
    
    /**
    *
    * @param cod 传入的编码
    * @param msg 信息
    * @param object 传入的对象
    * @return
    */
   public static Result result(String cod,String msg,Object object){
       Result result = new Result();
       result.setCode(cod);
       result.setMessage(msg);
       result.setData(object);
       return result;
   }
}
