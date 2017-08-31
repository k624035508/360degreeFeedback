package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 评测维度表
 * Created by jielao on 2017/8/4.
 */
@Entity
@Table(name = "t_feedback_dimension")
public class FeedbackDimension extends IdEntity {
    //维度名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
