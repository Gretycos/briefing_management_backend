package com.briefing_management.login.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private SessionRegistry sessionRegistry;

    @RequestMapping("/success")
    public void success(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://127.0.0.1:8091/about");
    }

    @RequestMapping("/mylogin")
    public void mylogin(HttpServletResponse response) throws IOException{
        response.sendRedirect("http://127.0.0.1:8091/login");
    }
}
