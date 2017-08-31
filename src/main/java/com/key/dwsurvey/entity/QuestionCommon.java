package com.key.dwsurvey.entity;

import com.key.common.QuType;
import com.key.common.base.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="t_question_common")
public class QuestionCommon extends IdEntity {

    //填空的input
    private Integer answerInputWidth;
    private Integer answerInputRow;

    private Date createDate;
    private String quTitle;
    private String quNote;
    private QuType quType;
    private Integer tag;
    //是否显示 0不显示   1显示
    private Integer visibility;

    public Integer getAnswerInputWidth() {
        return answerInputWidth;
    }

    public void setAnswerInputWidth(Integer answerInputWidth) {
        this.answerInputWidth = answerInputWidth;
    }

    public Integer getAnswerInputRow() {
        return answerInputRow;
    }

    public void setAnswerInputRow(Integer answerInputRow) {
        this.answerInputRow = answerInputRow;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getQuTitle() {
        return quTitle;
    }

    public void setQuTitle(String quTitle) {
        this.quTitle = quTitle;
    }

    public String getQuNote() {
        return quNote;
    }

    public void setQuNote(String quNote) {
        this.quNote = quNote;
    }

    public QuType getQuType() {
        return quType;
    }

    public void setQuType(QuType quType) {
        this.quType = quType;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
}
