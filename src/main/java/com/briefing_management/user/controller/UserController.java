package com.briefing_management.user.controller;

import com.briefing_management.role.model.Role;
import com.briefing_management.role.service.RoleService;
import com.briefing_management.user.model.User;
import com.briefing_management.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    RoleService roleService;

//    @RequestMapping(value = "/add",method = RequestMethod.POST)
//    public int addUser(@RequestParam Map<String,String> newUser){
//        return userService.addUser(new User(
//                null,
//                newUser.get("username"),
//                newUser.get("password"),
//                roleService.getRoleById(1)
//        ));
//    }
}
