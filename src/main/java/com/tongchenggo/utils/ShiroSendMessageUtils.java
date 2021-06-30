package com.tongchenggo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongchenggo.vo.ResultVO;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
* Shiro发送异常信息工具类
* @author jiang
* @date 2021.6.11
* */
public class ShiroSendMessageUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();
    /*
     * response发送响应数据ResultVO
     */
    public static void sendResponse(ServletResponse response, ResultVO resultVO) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()){
            out.print(objectMapper.writeValueAsString(resultVO));
        }catch (Exception e){
            throw e;
        }
    }

}
