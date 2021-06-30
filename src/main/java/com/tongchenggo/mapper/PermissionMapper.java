package com.tongchenggo.mapper;

import com.tongchenggo.entity.Permission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 权限表操作接口
* @author jiang
* @date 2021.6.11
* */
@Repository
public interface PermissionMapper {
    @Results(id = "permissionResultMap",value = {
            @Result(column = "pid",property = "ID",id = true),
            @Result(column = "permission",property = "permission")
    })
    //通过角色ID查询该角色拥有的权限
    @Select("select * from sys_permission p where p.pid in(select rp.pid from role_permission rp where rp.rid=#{rid})")
    List<Permission> getPermission(Integer rid);
}
