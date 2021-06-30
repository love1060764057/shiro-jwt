package com.tongchenggo.entity;

import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.List;

/*
* 角色类
* @author jiang
* @date 2021.6.11
* */
@Component
public class Role implements Serializable {
    private Integer ID;
    private String role;
    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
