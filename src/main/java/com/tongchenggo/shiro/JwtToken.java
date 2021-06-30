package com.tongchenggo.shiro;

import org.apache.shiro.authc.AuthenticationToken;


/*
* 自定义token
* @author jiang
* @date 2021.6.11
* */
public class JwtToken implements AuthenticationToken {
    private String token;
    public JwtToken(String token) {
        this.token = token;
    }
    //将token作为返回值
    @Override
    public String getPrincipal() {
        return this.token;
    }

    @Override
    public String getCredentials() {
        return this.token;
    }
}
