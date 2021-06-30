package com.tongchenggo.exception;

/*
* 自定义异常
* @author jiang
* @date 2021.6.11
* */

public class MyException extends RuntimeException {
    private Integer code = 0;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
