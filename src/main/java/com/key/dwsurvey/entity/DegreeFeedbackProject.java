package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by jielao on 2017/7/19.
 */

@Entity
@Table(name="t_degree_feedback_project")
public class DegreeFeedbackProject extends IdEntity {
    //测评名字
    private String name;
    //测评简介
    private String description;
    //测评创建者
    private String userId;
    //测评创建时间
    private Date createDate;
    //问卷状态  0默认设计状态  1执行中 2结束
    private Integer feedbackState = 0;
    //是否显示  1显示 0不显示
    private Integer visibility=1;

    public String getName(){ return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription(){ return description; }

    public void setDescription(String description) { this.description =description; }

    public String getUserId(){ return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public Date getCreateDate() { return createDate; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Integer getFeedbackState() { return feedbackState; }

    public void setFeedbackState(Integer feedbackState) { this.feedbackState = feedbackState; }

    public Integer getVisibility() { return visibility; }

    public void setVisibility(Integer visibility) { this.visibility = visibility; }

    private List<Question> questions=null;
    @Transient
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

    private FeedbackDetail feedbackDetail;
    @Transient
    public FeedbackDetail getFeedbackDetail() {
        return feedbackDetail;
    }

    public void setFeedbackDetail(FeedbackDetail feedbackDetail) {
        this.feedbackDetail = feedbackDetail;
    }
}
