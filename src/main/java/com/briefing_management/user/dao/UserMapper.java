package com.briefing_management.user.dao;

import com.briefing_management.role.model.Role;
import com.briefing_management.user.model.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Results(id = "getUser",
             value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", javaType = Role.class,column = "role",
                    one = @One(select = "com.role.dao.RoleMapper.getRoleById"))
             })
    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String Username);

    @Select("select * from user where id = #{id}")
    @ResultMap("getUser")
    User getUserById(@Param("id") String id);

    @Insert("insert into user(username,password,role) values(#{username},#{password},#{role})")
    int addUser(@Param("username")String username,@Param("password")String password,@Param("role")int role);
}
