package com.briefing_management.configuration;

import com.alibaba.fastjson.JSONObject;
import com.briefing_management.role.model.Role;
import com.briefing_management.user.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (loginAttemptService.isBlocked(username)) {
//            throw new BizException(201,String.format("username:{%s} login fail to limited,is locked",username));
//        }
        com.briefing_management.user.model.User definedUser = userService.getUserByUsername(username);
        if (definedUser==null){
            System.out.println("====================Username :" + username + ",not found======================");
            throw new UsernameNotFoundException("Username :" + username + ",not found");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Role role = definedUser.getRole();
        if (role != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
            authorities.add(authority);
        }
        User userDetails = new User(definedUser.getId(), definedUser.getPassword(), authorities);
        System.out.println("认证成功,用户信息: " + JSONObject.toJSONString(userDetails));
        return userDetails;
    }
}
