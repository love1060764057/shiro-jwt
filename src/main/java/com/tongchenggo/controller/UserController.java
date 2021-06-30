package com.tongchenggo.controller;

import com.tongchenggo.vo.ResultVO;
import com.tongchenggo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* 用户注册、登录
* 测试权限控制
* @author jiang
* @date 2021.6.11
* */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/add/{userName}/{password}")
    public ResultVO register(@PathVariable String userName,@PathVariable String password){
        return userService.register(userName,password);
    }
    @RequestMapping("/post/{userName}/{password}")
    public ResultVO login(@PathVariable String userName,@PathVariable String password){
        return userService.login(userName,password);
    }
    @RequestMapping("/get07")
    public ResultVO get07(){
        return  ResultVO.success("已认证普通用户，有查询权限");
    }
    @RequestMapping("/get06")
    public ResultVO get06(){
        return  ResultVO.success("已认证，身份为管理员或普通用户，且需有添加权限才可访问");
    }
    @RequestMapping("/get05")
    public ResultVO get05(){
        return  ResultVO.success("已认证普通用户");
    }
    @RequestMapping("/get04")
    public ResultVO get04(){
        return  ResultVO.success("已认证且是管理员角色拥有删除权限");
    }
    @RequestMapping("/get03")
    public ResultVO get03(){
        return  ResultVO.success("已认证且是管理员角色");
    }
    @RequestMapping("/get02")
    public ResultVO get02(){
        return  ResultVO.success("已认证用户且有添加权限");
    }
    @RequestMapping("/get01")
    public ResultVO get01(){
        return  ResultVO.success("已认证用户");
    }

    @RequestMapping("/logout")
    public ResultVO logout(){
       return userService.logout();
    }

}
