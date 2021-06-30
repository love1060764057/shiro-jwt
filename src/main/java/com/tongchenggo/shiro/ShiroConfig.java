package com.tongchenggo.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/*
* shiro配置类
* @author jiang
* @date 2021.6.11
* */
@Configuration
public class ShiroConfig {
    /*
     * 配置密码匹配器
     * */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(10);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
    /*
     * 配置自定义realm，设置密码匹配器
     * */
    @Bean
    public PasswordRealm passwordRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return passwordRealm;
    }
    @Bean
    public JwtRealm jwtRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return jwtRealm;
    }

    /*
     * 配置安全管理器，设置自定义realm，禁用session
     */
    @Bean
    public SecurityManager securityManager(PasswordRealm passwordRealm, JwtRealm jwtRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        Collection reams = new ArrayList();
        reams.add(passwordRealm);
        reams.add(jwtRealm);
        securityManager.setRealms(reams);
        //禁用session
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(defaultSubjectDAO);
        return  securityManager;
    }
    /*
     * @author jiang
     * @date 2021.6.11
     *
     * 配置shiro拦截器
     * shiro内置过滤器，可以实现权限相关拦截
     * 常用过滤器：
     * anon：无需认证，即无需登录就可以访问
     * authc：必须认证才可以访问
     * user：如果使用rememberMe的功能才可以直接访问
     * perms：必须拥有perms[xx]权限才可以访问
     * roles：必须拥有roles[xx]权限才可以访问
     * */

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //添加自定义拦截器filter
        shiroFilterFactoryBean.getFilters().put("jwt", new JwtFilter());
        //身份认证失败跳转到登录页面
        //该方法需要使用authc才会生效，这里使用使用自定义拦截器jwt验证身份，所以该方法不会生效
        shiroFilterFactoryBean.setLoginUrl("/error/unlogin");
        //身份认证成功跳转
        //前后分离项目中前端会用自己的逻辑在登录成功后进行跳转
//        shiroFilterFactoryBean.setSuccessUrl("/ok");
        //未授权页面
        //该方法需要roles或者perms拦截器才会生效，若使用自定义拦截器依然不会生效
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/unauthorized");
        //创建拦截链集合，LinkedHashMap有序集合
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        //定义拦截规则
        //anon无需认证，jwt为自定义拦截器
        filterMap.put("/user/add/**","anon");
        filterMap.put("/user/post/**","anon");
        filterMap.put("/user/get01","jwt");
        filterMap.put("/user/get02","jwt,perms[add]");
        filterMap.put("/user/get03","jwt,roles[admin]");
        filterMap.put("/user/get04","jwt,roles[admin],perms[delete]");
        filterMap.put("/user/get05","jwt,roles[ordinary]");
        filterMap.put("/user/get06","jwt,roles[ordinary],perms[add]");
        filterMap.put("/user/get07","jwt,roles[ordinary],perms[find]");
        filterMap.put("/user/**", "jwt");
        //注入拦截链到拦截器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //注入安全管理器到拦截器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }
}
