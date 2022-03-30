package com.maxt.system.hospital.common.common.util.util;

import com.maxt.system.hospital.common.common.util.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午2:39
 * @Version 1.0
 * @Description  获取当前用户信息工具类
 */
public class AuthContextHolder {

    //获取当前用户id
    public static Long getUserId(HttpServletRequest request){
        //从header中获取token
        String token = request.getHeader("token");
        //jwt从token中获取userId
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }

    //获取当前用户名称
    public static String getUserName(HttpServletRequest request){
        //从header中获取token
        String token = request.getHeader("token");
        //jwt从token中获取userName
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}
