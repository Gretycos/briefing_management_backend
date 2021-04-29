package com.briefing_management.configuration.handler;

import com.alibaba.fastjson.JSONObject;
import com.briefing_management.configuration.util.Result;
import com.briefing_management.configuration.util.ResultCode;
import com.briefing_management.configuration.util.ResultUtil;
import com.briefing_management.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义登录成功Handler，返回json
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        User security_user = (User)authentication.getPrincipal();
//        com.briefing_management.user.model.User user = userService.getUserById(security_user.getUsername());
//        user.setPassword(null);
        Result result = ResultUtil.success(ResultCode.SUCCESS, "登录成功！",security_user);
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
