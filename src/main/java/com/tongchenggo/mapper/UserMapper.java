package com.tongchenggo.mapper;

import com.tongchenggo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/*
* 用户表操作接口
* @author jiang
* @date 2021.6.11
* */
@Repository
public interface UserMapper {

    @Results(id = "userResultMap",value = {
            @Result(column = "uid",property = "ID" ,id = true),
            @Result(column = "userName" ,property = "userName"),
            @Result(column = "password",property = "password")

    })

    //通过用户名查询用户、密码
    @Select("select * from sys_user u where u.userName=#{userName}")
    User getUserByName(String userName);

    @Results(id="userInfoResultMap" , value = {
            @Result(column = "uid",property = "ID" ,id = true),
            @Result(column = "userName" ,property = "userName"),
            @Result(column = "password",property = "password"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "rid",property ="role", one=@One(select = "com.tongchenggo.mapper.RoleMapper.getRoleByrid") ),

    })
    //通过用户名查询用户、角色
    @Select("select * from sys_user u where u.userName=#{userName}")
    User getUserRoleByName(String userName);

    //添加用户
    @Insert("insert into sys_user values(null,#{userName},#{password},#{rid})")
    void addUser(User user);
}
