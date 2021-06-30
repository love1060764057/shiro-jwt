package com.tongchenggo.entity;

import org.springframework.stereotype.Component;
import java.io.Serializable;

/*
* 权限类
* @author jiang
* @date 2021.6.11
* */
@Component
public class Permission implements Serializable {
    private Integer ID;
    private String permission;
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
