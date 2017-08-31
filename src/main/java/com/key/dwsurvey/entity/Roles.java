package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限表
 * Created by jielao on 2017/8/26.
 */
@Entity
@Table(name = "t_roles")
public class Roles extends IdEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
