package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 指标考核评分选项
 * Created by jielao on 2017/7/24.
 */

@Entity
@Table(name = "t_degree_feedback_item_item")
public class DegreeFeedbackItemItem extends IdEntity {

    //所属指标行
    private String itemId;
    private String name;
    private String description;
    // 排序号
    private Integer orderById;
    // 是否显示 0不显示  1显示
    private Integer visibility = 1;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderById() {
        return orderById;
    }

    public void setOrderById(Integer orderById) {
        this.orderById = orderById;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    private float avgScore = 0f;

    @Transient
    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }
}
