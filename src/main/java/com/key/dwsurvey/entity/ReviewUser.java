package com.key.dwsurvey.entity;

import com.key.common.base.entity.IdEntity;

/**
 * 临时保存考核表员工关系
 * Created by jielao on 2017/8/19.
 */
public class ReviewUser extends IdEntity {
    private String examinee;
    private String investigateTop;
    private String investigateMiddle;
    private String investigateBottom;

    public String getExaminee() {
        return examinee;
    }

    public void setExaminee(String examinee) {
        this.examinee = examinee;
    }

    public String getInvestigateTop() {
        return investigateTop;
    }

    public void setInvestigateTop(String investigateTop) {
        this.investigateTop = investigateTop;
    }

    public String getInvestigateMiddle() {
        return investigateMiddle;
    }

    public void setInvestigateMiddle(String investigateMiddle) {
        this.investigateMiddle = investigateMiddle;
    }

    public String getInvestigateBottom() {
        return investigateBottom;
    }

    public void setInvestigateBottom(String investigateBottom) {
        this.investigateBottom = investigateBottom;
    }
}
