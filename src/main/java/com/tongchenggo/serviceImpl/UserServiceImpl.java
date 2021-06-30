package com.tongchenggo.serviceImpl;

import com.tongchenggo.constant.Constant;
import com.tongchenggo.utils.MD5Utils;
import com.tongchenggo.vo.ResultVO;
import com.tongchenggo.entity.User;
import com.tongchenggo.utils.JWTUtil;
import com.tongchenggo.mapper.UserMapper;
import com.tongchenggo.service.UserService;
import com.tongchenggo.vo.Token;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
* 用户相关服务接口实现
* @author jiang
* @date 2021.6.11
* */
@Service
public class UserServiceImpl implements UserService {
    public static Integer ROLE=1;
    @Autowired
    private UserMapper userMapper;
    /*
    * 用户登录
    * 返回登录状态，成功携带token，失败返回失败信息
    * */
    @Override
    public ResultVO login(String userName, String password) {
        //判断用户账号或密码是否为空
        if(userName==null|"".equals(userName)|password==null|"".equals(password)){
            return ResultVO.fail(Constant.FAIL_CODE,"账号或密码不能为空");
        }

        //根据用户名密码创建token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName,password);
        usernamePasswordToken.setRememberMe(false);
        //获取认证主体subject
        Subject subject = SecurityUtils.getSubject();
        try{
            //根据用户信息登录
            subject.login(usernamePasswordToken);
        }catch (Exception e){
            return ResultVO.fail(Constant.FAIL_CODE,"登录失败");
        }
        String token = null;
        try {
            token=JWTUtil.sign(userName);
        }catch (Exception e){
            e.getStackTrace();
        }
        return ResultVO.success("登录成功",new Token(token));
    }
    /*
    * 用户注册
    * 返回注册状态
    * */
    @Override
    public ResultVO register(String userName, String password) {
        //判断用户账号、密码、角色、权限是否为空
        if(userName==null|"".equals(userName)|password==null|"".equals(password)){
            return ResultVO.fail(Constant.FAIL_CODE,"账号、密码、角色、权限任一一项不可为空");
        }
        //根据用户名查询用户判断是否存在该用户
        User user = userMapper.getUserByName(userName);
        if(user==null){
            user = new User();
            user.setUserName(userName);
            user.setPassword(MD5Utils.encryptionPassword(password,userName));
            user.setRid(ROLE);
            try {
                userMapper.addUser(user);//添加用户
            }catch (Exception e){
                return ResultVO.fail(Constant.FAIL_CODE,"注册失败");
            }
            return ResultVO.success("注册成功");
        }else {
            return ResultVO.fail(Constant.FAIL_CODE,"用户已存在");
        }
    }
    /*
    * 用户退出
    * 返回退出结果
    * */
    public ResultVO logout(){
        SecurityUtils.getSubject().logout();
        return ResultVO.success("退出成功！");
    }
    /*
    * 根据用户名获取用户
    * */
    public User getUserByName(String userName){
        return userMapper.getUserRoleByName(userName);
    }
}
