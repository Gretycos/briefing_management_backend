package com.briefing_management.configuration.handler;

import com.alibaba.fastjson.JSONObject;
import com.briefing_management.configuration.util.Result;
import com.briefing_management.configuration.util.ResultCode;
import com.briefing_management.configuration.util.ResultUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义登录失败Handler，返回json
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        Result result = new Result();
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            result = ResultUtil.error(ResultCode.ERROR, "用户名或密码输入错误，登录失败!");
        } else if (e instanceof DisabledException) {
            result = ResultUtil.error(ResultCode.ERROR, "账户被禁用，登录失败，请联系管理员!");
        } else {
            result = ResultUtil.error(ResultCode.ERROR, "登录失败:" + e.fillInStackTrace());
        }
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
