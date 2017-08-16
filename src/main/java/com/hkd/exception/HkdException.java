package com.hkd.exception;

/**
 * Created by 1 on 2017-08-16.
 */
public class HkdException extends RuntimeException {
    private static final long serialVersionUID = -1835206418558868789L;

    private String errorMsg;

    private int errorCode;

    public HkdException() {
        super();
    }

    public HkdException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public HkdException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "HkdException{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
