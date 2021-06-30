package com.tongchenggo.controller;

import com.tongchenggo.constant.Constant;
import com.tongchenggo.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* shiro身份未认证和未授权转发控制器
* @author jiang
* @date 2021.6.11
* */
@RestController
@RequestMapping("error")
public class ErrorController {
    @RequestMapping("/unauthorized")
    public ResultVO unauthorized(){
      return ResultVO.fail(Constant.FAIL_CODE,"无访问权限");
    }
    @RequestMapping("/unlogin")
    public ResultVO unlogin(){
      return ResultVO.fail(Constant.FAIL_CODE,"未登录用户");
    }
}
