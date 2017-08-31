package com.key.dwsurvey.action;

import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.DegreeFeedbackProject;
import com.key.dwsurvey.service.DegreeFeedbackProjectManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;
import java.util.Date;

/**
 * 创建测评
 * Created by jielao on 2017/7/19.
 */

@Namespace("/design")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
        @Result(name=ActionSupport.SUCCESS, location="/WEB-INF/page/content/diaowen-create/create_empty.jsp", type= Struts2Utils.DISPATCHER),
        @Result(name="design", location = "/design/my-feedback-design.action?feedbackId=${feedbackId}", type = Struts2Utils.REDIRECT)
})
public class MyFeedbackCreateAction extends ActionSupport {
    @Autowired
    private DegreeFeedbackProjectManager degreeFeedbackProjectManager;

    private String feedbackId;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String save() throws Exception {
        String feedbackname = Struts2Utils.getParameter("feedbackName");
        DegreeFeedbackProject feedback = new DegreeFeedbackProject();
        try{
            if(feedbackname == null || "".equals(feedbackname.trim())){
                feedbackname = "请输入评测标题";
            } else {
                feedbackname = URLDecoder.decode(feedbackname, "utf-8");
            }
            feedback.setName(feedbackname);
            feedback.setCreateDate(new Date());
            degreeFeedbackProjectManager.save(feedback);
            feedbackId = feedback.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "design";
    }

    public String getFeedbackId() {
        return feedbackId;
    }
}
