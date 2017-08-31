package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by jielao on 2017/8/5.
 */

@Entity
@Table(name = "t_review_dimension")
public class ReviewDimension extends IdEntity {
    //所选维度
    private String dimensionId;
    //权重
    private String weight;
    //所属考核表
    private String reviewId;
    //考核者
    private String investigate;
    //被考核者
    private String examinee;

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getInvestigate() {
        return investigate;
    }

    public void setInvestigate(String investigate) {
        this.investigate = investigate;
    }

    public String getExaminee() {
        return examinee;
    }

    public void setExaminee(String examinee) {
        this.examinee = examinee;
    }

    private String selectInvestigate;

    @Transient
    public String getSelectInvestigate() {
        return selectInvestigate;
    }

    public void setSelectInvestigate(String selectInvestigate) {
        this.selectInvestigate = selectInvestigate;
    }
}
