package com.bishe.result;

import lombok.Data;

@Data
public class Result<T> {

    public static final String CODE_SUCCESS = "200";
    public static final String CODE_AUTH_ERROR = "401";
    public static final String CODE_SYS_ERROR = "500";


    private String code;
    private String message;
    private boolean isSuccess;

    private T data;

    public Result() {

    }

    public Result(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void success() {
        this.setCode(CODE_SUCCESS);
        this.setSuccess(true);
        this.setMessage("请求成功");
    }

    public void success(T data) {
        this.setCode(CODE_SUCCESS);
        this.setSuccess(true);
        this.setMessage("success");
        this.setData(data);
    }

    public void error(String msg) {
        this.setCode(CODE_SYS_ERROR);
        this.setSuccess(false);
        this.setMessage(msg);
    }

    public void error(String code, String msg) {
        this.setCode(code);
        this.setSuccess(false);
        this.setMessage(msg);
    }

    public void error() {
        this.setCode(CODE_SYS_ERROR);
        this.setSuccess(false);
        this.setMessage("系统错误");
    }


}
