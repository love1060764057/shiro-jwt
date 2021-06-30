package com.tongchenggo.entity;

import org.springframework.stereotype.Component;
import java.io.Serializable;
/*
* 用户类
* @author jiang
* @date 2021.6.11
* */
@Component
public class User implements Serializable {
    private Integer ID;
    private String userName;
    private String password;
    private Integer rid;
    private Role role;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
