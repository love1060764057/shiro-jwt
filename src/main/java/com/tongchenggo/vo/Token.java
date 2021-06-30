package com.tongchenggo.vo;

import lombok.Data;

/*
* @author jiang
* @date 2021.6.11
* */
@Data
public class Token {
    private String token;
    public Token(String token){
        this.token = token;
    }
}
