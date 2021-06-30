package com.tongchenggo.shiro;


import com.tongchenggo.constant.Constant;
import com.tongchenggo.vo.ResultVO;
import com.tongchenggo.utils.ShiroSendMessageUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.StringUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/*
* @author jiang
* @date 2021.6.11
* 自定义身份认证拦截器
* 认证流程：
* OncePerRequestFilter#doFilter=>AdviceFilter#doFilterInternal=>PathMatchingFilter#preHandle=>AccessControlFilter#onPreHandle
* AccessControlFilter#onPreHandle会调用isAccessAllowed和onAccessDenied方法来确认是否通过认证
* public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
*       return isAccessAllowed(request, response, mappedValue) || onAccessDenied(request, response, mappedValue);
*   }
* */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /*
    * 重写createToken方法
    * 创建自定义的JwtToken
    * */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 获取请求头Authorization的值
        String authorization = getAuthzHeader(request);
        return new JwtToken(authorization);
    }

    /*
     * 重写isLoginAttempt方法
     * 判断用户是否想要登录。
     * 检测header里面是否包含Authorization字段，即token
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    /*
     * 重写executeLogin方法
     * 执行登录操作
     * 登录异常里发送自定义异常信息给用户
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException e) {
                ResultVO resultVO = ResultVO.fail("用户认证失败，请确认是否登录或登录已超时");
                ShiroSendMessageUtils.sendResponse(response, resultVO);
                return this.onLoginFailure(token, e, request, response);
            }
        }
    }

    /*
     * 重写onAccessDenied
     * isAccessAllowed身份认证未通过，执行此方法
     * 返回true，请求一定会通过
     * 返回false，结束过滤链
     * 这里直接返回false处理错误提示
     *
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String authorization = getAuthzHeader(request);
        if (StringUtils.isEmpty(authorization)){
            ShiroSendMessageUtils.sendResponse(response, ResultVO.fail(Constant.TOKEN_FAILURE_CODE,"请求未携带token，请确认是否登录！"));

        }
        return false;
    }
    /*
    * 重写isAccessAllowed方法
    * 是否允许访问
    * 检查未携带token拒绝访问,在onAccessDenied方法里发送无token信息给用户，有token调用登录请求
    * */

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean loggedIn=false;
        if (isLoginAttempt(request, response)) {
            try {
                loggedIn = executeLogin(request, response);
            } catch (Exception e) {
                loggedIn = false;
            }
        }
        return loggedIn;
    }



//        @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest req =(HttpServletRequest)request;
//        HttpServletResponse res = (HttpServletResponse)response;
//        res.setHeader("Access-control-Allow-Origin",req.getHeader("Origin"));
//        res.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
//        res.setHeader("Access-Control-Allow-Headers",req.getHeader("Access-Control-Request-Headers"));
//        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
//            res.setStatus(HttpStatus.OK.value());
//            return false;
//        }
//        return super.preHandle(request, response);
//    }
}
