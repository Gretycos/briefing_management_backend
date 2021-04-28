package com.briefing_management.role.dao;

import com.briefing_management.role.model.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper {
    @Select("select * from role where id = #{id}")
    Role getRoleById(@Param("id") int id);
}
