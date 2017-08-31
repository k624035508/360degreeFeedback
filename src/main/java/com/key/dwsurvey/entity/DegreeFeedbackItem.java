package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 指标
 * Created by jielao on 2017/7/24.
 */

@Entity
@Table(name = "t_degree_feedback_item")
public class DegreeFeedbackItem extends IdEntity {

    //指标名称
    private String name;
    //指标说明
    private String description;
    private String quId;
    private Integer orderById;
    private Integer visibility = 1;

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

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
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

    public List<DegreeFeedbackItemItem> itemList;
    @Transient

    public List<DegreeFeedbackItemItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<DegreeFeedbackItemItem> itemList) {
        this.itemList = itemList;
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
