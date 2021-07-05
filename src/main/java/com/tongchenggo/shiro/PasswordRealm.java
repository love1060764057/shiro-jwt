package com.tongchenggo.shiro;

import com.tongchenggo.exception.MyShiroException;
import com.tongchenggo.entity.User;
import com.tongchenggo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/*
* 密码验证类
* @author jiang
* @date 2021.6.11
* */
public class PasswordRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /*
    *subject.login(token)方法中的token是UsernamePasswordToken时，调用此Realm的doGetAuthenticationInfo
    * 必须重写此方法
    *  */
    @Override
    public boolean supports(AuthenticationToken token) {
        return  token instanceof UsernamePasswordToken;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            System.out.println("PasswordRealm执行");
                //通过authenticationToken获取用户名
                String userName = (String) authenticationToken.getPrincipal();
                //根据用户名从数据库查询用户信息
                User user = userService.getUserByName(userName);
                //判断用户是否存在
                if (user != null) {
                    //认证用户信息
                    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "passwordRealm");
                    //设置盐值加密
                    authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
                    return authenticationInfo;
                } else {
                    throw new MyShiroException("用户不存在");
                }

    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
