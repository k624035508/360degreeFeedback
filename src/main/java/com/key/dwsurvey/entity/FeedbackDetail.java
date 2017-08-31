package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jielao on 2017/8/29.
 */

@Entity
@Table(name = "t_feedback_detail")
public class FeedbackDetail extends IdEntity{
    private String dirId;
    private String ownWeight;
    private String topWeight;
    private String middleWeight;
    private String bottomWeight;
    private String feedbackNote;

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String getOwnWeight() {
        return ownWeight;
    }

    public void setOwnWeight(String ownWeight) {
        this.ownWeight = ownWeight;
    }

    public String getTopWeight() {
        return topWeight;
    }

    public void setTopWeight(String topWeight) {
        this.topWeight = topWeight;
    }

    public String getMiddleWeight() {
        return middleWeight;
    }

    public void setMiddleWeight(String middleWeight) {
        this.middleWeight = middleWeight;
    }

    public String getBottomWeight() {
        return bottomWeight;
    }

    public void setBottomWeight(String bottomWeight) {
        this.bottomWeight = bottomWeight;
    }

    public String getFeedbackNote() {
        return feedbackNote;
    }

    public void setFeedbackNote(String feedbackNote) {
        this.feedbackNote = feedbackNote;
    }
}
