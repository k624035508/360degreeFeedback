package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;
import com.key.common.base.entity.User;
import org.hibernate.annotations.Formula;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 考核测评分配
 * Created by jielao on 2017/7/31.
 */
@Entity
@Table(name = "t_feedback_review")
public class FeedbackReview extends IdEntity {
    //测评名称
    private String name;
    //所用模板Id
    private String feedbackDemoId;
    //被考核者
    private String examinee;

    private Date createDate;
    private Date finishDate;
    //创建者
    private String userId;

    private String publishStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedbackDemoId() {
        return feedbackDemoId;
    }

    public void setFeedbackDemoId(String feedbackDemoId) {
        this.feedbackDemoId = feedbackDemoId;
    }

    public String getExaminee() {
        return examinee;
    }

    public void setExaminee(String examinee) {
        this.examinee = examinee;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    //所选用户名字
    private String selectExaminee;

    @Transient
    public String getSelectExaminee() {
        return selectExaminee;
    }

    public void setSelectExaminee(String selectExaminee) {
        this.selectExaminee = selectExaminee;
    }

    //所选用户
    public List<User> userList;

    @Transient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    //所选维度
    private List<ReviewDimension> reviewDimensionList;

    @Transient
    public List<ReviewDimension> getReviewDimensionList() {
        return reviewDimensionList;
    }

    public void setReviewDimensionList(List<ReviewDimension> reviewDimensionList) {
        this.reviewDimensionList = reviewDimensionList;
    }

    //用户名
    private String userName;
    @Formula("(select o.name from t_user o where o.id = user_id)")
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}