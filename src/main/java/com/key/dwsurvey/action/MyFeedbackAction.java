package com.key.dwsurvey.action;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.DegreeFeedbackProject;
import com.key.dwsurvey.service.DegreeFeedbackProjectManager;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 我的测评模板
 * Created by jielao on 2017/7/21.
 */
@Namespace("/design")
@InterceptorRefs({@InterceptorRef("paramsPrepareParamsStack")})
@Results({
        @Result(name = MyFeedbackAction.SUCCESS, location = "/WEB-INF/page/content/diaowen-design/listFeedback.jsp", type = Struts2Utils.DISPATCHER),
        @Result(name = "design", location = "/design/my-feedback-design.action?feedbackId=${id}", type = Struts2Utils.REDIRECT)
})
@AllowedMethods({"feedbackState", "attrs"})
public class MyFeedbackAction extends CrudActionSupport<DegreeFeedbackProject, String> {
    @Autowired
    private DegreeFeedbackProjectManager degreeFeedbackProjectManager;

    @Autowired
    private AccountManager accountManager;

    @Override
    public String list() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        String feedbackState = request.getParameter("feedbackState");
        if(feedbackState == null || "".equals(feedbackState)){
//            entity.setFeedbackState(null);
        }
        page = degreeFeedbackProjectManager.findByUser(page, entity);
        return SUCCESS;
    }

    public String delete() throws Exception{
        HttpServletResponse response = Struts2Utils.getResponse();
        String result = "false";
        try{
            User user = accountManager.getCurUser();
            if(user != null){
                DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedbackByUser(id, user.getId());
                if(degreeFeedbackProject != null){
                    degreeFeedbackProjectManager.delete(id);
                    result = "true";
                }
            }
        } catch (Exception e){
            result="false";
        }
        response.getWriter().write(result);
        return null;
    }

    //测评壮态设置
    public String feedbackState() throws Exception{
        HttpServletResponse response = Struts2Utils.getResponse();
        String result = "";
        try{
            User user = accountManager.getCurUser();
            if(user!=null){
                String userId = user.getId();
                DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedbackByUser(id, userId);
                if(degreeFeedbackProject != null){
                    int feedbackState = entity.getFeedbackState();
                    degreeFeedbackProject.setFeedbackState(feedbackState);
                }
            }
            result = "true";
        } catch (Exception e) {
            e.printStackTrace();
            result = "error";
        }
        response.getWriter().write(result);
        return null;
    }

    public String attrs() throws Exception {
        HttpServletRequest request=Struts2Utils.getRequest();
        HttpServletResponse response=Struts2Utils.getResponse();
        try{
            DegreeFeedbackProject feedback=degreeFeedbackProjectManager.getFeedback(id);
            JsonConfig cfg = new JsonConfig();
            cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
            JSONObject jsonObject=JSONObject.fromObject(feedback,cfg);
            response.getWriter().write(jsonObject.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
