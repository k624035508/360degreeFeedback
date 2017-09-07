package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 测评考核回答
 * Created by jielao on 2017/8/1.
 */

@Entity
@Table(name = "t_feedback_answer")
public class FeedbackAnswer extends IdEntity {

    //回答的考核表
    private String reviewId;
    //被评测的用户
    private String ownerId;
    //回答的用户
    private String answerUser;
    //回答所选类型(维度)
    private String dimensionId;
    //回答时间
    private Date answerDate;
    //回答类型  0:草稿  1:直接回答
    private Integer answerStatus;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public String getDimensionId() { return dimensionId; }

    public void setDimensionId(String dimensionId) { this.dimensionId = dimensionId; }

    public Integer getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Integer answerStatus) {
        this.answerStatus = answerStatus;
    }

    private List<AnFeedbackItem> anFeedbackItemList;

    @Transient
    public List<AnFeedbackItem> getAnFeedbackItemList() {
        return anFeedbackItemList;
    }

    public void setAnFeedbackItemList(List<AnFeedbackItem> anFeedbackItemList) {
        this.anFeedbackItemList = anFeedbackItemList;
    }
}
