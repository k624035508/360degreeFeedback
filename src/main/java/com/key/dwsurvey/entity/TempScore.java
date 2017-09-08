package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import java.math.BigDecimal;

/**
 * 临时保存考核成绩
 * Created by jielao on 2017/8/9.
 */
public class TempScore extends IdEntity {
    private String userId;
    private String dimensionId;
    private Float avgScores = 0f;
    private String itemItemId;
    private String weight;
    private String answerScore;

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Float getAvgScores() {
        return avgScores;
    }

    public void setAvgScores(Float avgScores) {
        this.avgScores = avgScores;
    }

    public String getItemItemId() {
        return itemItemId;
    }

    public void setItemItemId(String itemItemId) {
        this.itemItemId = itemItemId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
    }
}
