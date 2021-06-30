package com.tongchenggo.exception;

import org.apache.shiro.authc.AuthenticationException;
/*
* 自定义shiro相关异常
* @author jiang
* @date 2021.6.11
* */
public class MyShiroException extends AuthenticationException {
    private Integer code=0;
    public MyShiroException(String message) {
        super(message);
    }

    public MyShiroException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
