package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;
import com.key.common.base.entity.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by jielao on 2017/7/31.
 */

@Entity
@Table(name = "T_department")
public class Department extends IdEntity {
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    private List<User> userList;

    @Transient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
