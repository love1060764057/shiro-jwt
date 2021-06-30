package com.tongchenggo.shiro;

import com.tongchenggo.entity.Permission;
import com.tongchenggo.entity.User;
import com.tongchenggo.exception.MyShiroException;
import com.tongchenggo.service.UserService;
import com.tongchenggo.utils.JWTUtil;
import com.tongchenggo.constant.Constant;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

/*
* token校验类
* @author jiang
* @date 2021.6.11
* */
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /*
     *subject.login(token)方法中的token是JwtToken时，调用此Realm的doGetAuthenticationInfo
     * 必须重写此方法
     *  */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token!=null && token instanceof JwtToken;
    }
    /*
    * 校验token身份认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            System.out.println("JWTRealm执行");
            String token =(String)authenticationToken.getPrincipal();
            String userName = JWTUtil.getUsername(token);
            if(!JWTUtil.verify(token,userName)){
                throw new MyShiroException(Constant.TOKEN_FAILURE_CODE, "token无效，请重新登录");
            }else {
                //根据用户名从数据库查询用户信息
                User user = userService.getUserByName(userName);
                //判断用户是否存在
                if(user!=null){
                    //返回给客户端的token未加密，在shiro里比对时会根据规则加密后比对
                    //用户密码登录会根据用户输入密码加密后与数据库里加密过的密码对比
                    SimpleHash encryptionToken = new SimpleHash("MD5",
                            token, ByteSource.Util.bytes(userName),
                            10);
                    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,encryptionToken,ByteSource.Util.bytes(userName),"jwtRealm");

                    return authenticationInfo;
                }else{
                    throw new MyShiroException("token非法");
                }
            }
    }
    /*
    * 授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = userService.getUserByName(principalCollection.getPrimaryPrincipal().toString());
        List<String> perms = new ArrayList<String>();
        if(!CollectionUtils.isEmpty(user.getRole().getPermissionList())){
            for(Permission permission : user.getRole().getPermissionList()){
                perms.add(permission.getPermission());
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole().getRole());
        simpleAuthorizationInfo.addStringPermissions(perms);
        return simpleAuthorizationInfo;
    }

}
