package com.hkd.exception;

import com.hkd.entity.Result;
import com.hkd.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 1 on 2017-08-16.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);


    /**
     * 处理自定义异常
     *
     * @param hkdException
     * @return
     */
    @ExceptionHandler(value = HkdException.class)
    @ResponseBody
    public Result handleCustomException(HkdException hkdException) {
        if (hkdException.getErrorCode() == -2) {
            return ResultUtils.error(String.valueOf(hkdException.getErrorCode()), hkdException.getErrorMsg());
        } else {
            logger.error("【系统异常】{}", hkdException);
            return ResultUtils.error("-1", "未知错误");
        }
    }


    /**
     * 系统异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
/*    	 instanceof运算符用法
         运算符是双目运算符,左面的操作元是一个对象,右面是一个类.当
    	 左面的对象是右面的类创建的对象时,该运算符运算的结果是true,否则是false  */
        if (e instanceof HkdException) {
            HkdException hkdException = (HkdException) e;
            return ResultUtils.error(String.valueOf(hkdException.getErrorCode()), hkdException.getMessage());
        } else {
            logger.error("【系统异常】{}", e);
            return ResultUtils.error("-1", "未知错误");
        }
    }


}
