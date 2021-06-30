package com.tongchenggo.vo;


import com.tongchenggo.constant.Constant;
import java.io.Serializable;

/*
* 数据响应封装类
* @author jiang
* @date 2021.6.11
* */
public class ResultVO<T> implements Serializable {
    //响应状态
    private int status;
    //响应信息描述
    private String message;
    //响应数据
    private T data;

    public ResultVO(int status, String message){
        this.status = status;
        this.message = message;
    }
    public ResultVO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public static<T> ResultVO<T> success(String message,T data){
        return new ResultVO(Constant.SUCCESS_CODE,message,data);
    }
    public static<T> ResultVO<T> success(String message){
        return new ResultVO(Constant.SUCCESS_CODE,message);
    }

    public static ResultVO fail(String message){
        return new ResultVO(Constant.FAIL_CODE, message, null);
    }

    public static ResultVO fail(int status, String message){
        return new ResultVO(status, message, null);
    }


}
