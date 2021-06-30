package com.tongchenggo.mapper;

import com.tongchenggo.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/*
* 角色表操作接口
* @author jiang
* @date 2021.6.11
* */
@Repository
public interface RoleMapper {
    @Results(id="roleResultMap" , value = {
            @Result(column = "rid",property = "ID" ,id = true),
            @Result(column = "role" ,property = "role"),
            @Result(column = "rid",property = "permissionList",many = @Many(select = "com.tongchenggo.mapper.PermissionMapper.getPermission"))
    })
    //角色ID查询用户角色
    @Select("select * from sys_role where sys_role.rid=#{rid} ")
    Role getRoleByrid(Integer rid);

}
