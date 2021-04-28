package com.briefing_management.role.service;

import com.briefing_management.role.dao.RoleMapper;
import com.briefing_management.role.model.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService{
    @Resource
    RoleMapper roleMapper;


    @Override
    public Role getRoleById(int id) {
        return roleMapper.getRoleById(id);
    }
}
