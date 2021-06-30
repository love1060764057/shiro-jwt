package com.tongchenggo.service;

import com.tongchenggo.entity.User;
import com.tongchenggo.vo.ResultVO;

/*
* 用户相关服务接口
* @author jiang
* @date 2021.6.11
* */
public interface UserService {
    //登录
    ResultVO login(String userName, String password);
    //注册
    ResultVO register(String userName,String password);
    //退出
    ResultVO logout();
    //根据用户名获取用户
    User getUserByName(String userName);
}
