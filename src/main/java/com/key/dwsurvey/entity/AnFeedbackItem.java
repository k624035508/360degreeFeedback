package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 考评填写的分数
 * Created by jielao on 2017/8/2.
 */

@Entity
@Table(name = "t_an_feedback_item")
public class AnFeedbackItem extends IdEntity {
    //所属的答卷表信息
    private String belongAnswerId;
    //评分项目行里的id
    private String feedbackRowId;
    //回答分数
    private String answerScore;

    public AnFeedbackItem(){

    }

    public AnFeedbackItem(String belongAnswerId, String feedbackRowId, String answerScore){
        this.belongAnswerId = belongAnswerId;
        this.feedbackRowId = feedbackRowId;
        this.answerScore = answerScore;
    }

    public String getBelongAnswerId() {
        return belongAnswerId;
    }

    public void setBelongAnswerId(String belongAnswerId) {
        this.belongAnswerId = belongAnswerId;
    }

    public String getFeedbackRowId() {
        return feedbackRowId;
    }

    public void setFeedbackRowId(String feedbackRowId) {
        this.feedbackRowId = feedbackRowId;
    }

    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
    }
}
