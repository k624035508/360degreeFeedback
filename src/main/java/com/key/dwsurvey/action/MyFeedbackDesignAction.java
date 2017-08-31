package com.key.dwsurvey.action;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.*;
import com.key.dwsurvey.service.DegreeFeedbackProjectManager;
import com.key.dwsurvey.service.FeedbackReviewManager;
import com.key.dwsurvey.service.QuestionManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 * 设计测评问卷
 * Created by jielao on 2017/7/20.
 */

@Namespace("/design")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
        @Result(name= ActionSupport.SUCCESS,location="/WEB-INF/page/content/diaowen-design/feedback.jsp",type= Struts2Utils.DISPATCHER),
//        @Result(name=MySurveyDesignAction.PREVIEWDEV,location="/WEB-INF/page/content/diaowen-design/survey_preview_dev.jsp",type=Struts2Utils.DISPATCHER),
//        @Result(name=MySurveyDesignAction.COLLECTSURVEY,location="my-collect.action?surveyId=${surveyId}",type=Struts2Utils.REDIRECT),
        @Result(name=MySurveyDesignAction.RELOADDESIGN,location="/design/my-feedback-design.action?feedbackId=${feedbackId}",type=Struts2Utils.REDIRECT)
})
@AllowedMethods({"previewDev", "ajaxSave", "importExcel", "excelWrite"})
public class MyFeedbackDesignAction extends ActionSupport {

    protected final static String RELOADDESIGN = "reloadDesign";

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private QuestionManager questionManager;

    @Autowired
    private DegreeFeedbackProjectManager degreeFeedbackProjectManager;

    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    private String feedbackId;

    //接受拦截器传入的临时文件
    private File some;
    //接受拦截器注入的原始文件名
    private String someFileName;


    @Override
    public String execute() throws Exception{
        buildFeedback();
        return SUCCESS;
    }

    private void buildFeedback(){
        HttpServletRequest request = Struts2Utils.getRequest();
        //判断是否拥有权限
        User user= accountManager.getCurUser();
        if(user!=null){
            String userId = user.getId();
            DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedbackByUser(feedbackId, userId);
            if(degreeFeedbackProject != null){
                List<Question> questions = questionManager.findDetails(feedbackId, "2");
                degreeFeedbackProject.setQuestions(questions);
                degreeFeedbackProjectManager.save(degreeFeedbackProject);
                Struts2Utils.setReqAttribute("feedback", degreeFeedbackProject);
            } else {
                Struts2Utils.setReqAttribute("msg", "未登录或没有相应数据权限");
            }
        } else{
            Struts2Utils.setReqAttribute("msg", "未登录或没有相应数据权限");
        }
    }

    public String ajaxSave() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        //已经发布考核表不能修改
        List<FeedbackReview> feedbackReviewList = feedbackReviewManager.findByDemoId(feedbackId);
        if (feedbackReviewList.size() != 0) {
            response.getWriter().write("havePublish");
        }else {
            String svyName = request.getParameter("svyName");
            String svyNote = request.getParameter("svyNote");
            String ownWeight = request.getParameter("ownWeight");
            String topWeight = request.getParameter("topWeight");
            String middleWeight = request.getParameter("middleWeight");
            String bottomWeight = request.getParameter("bottomWeight");
            DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedback(feedbackId);
            FeedbackDetail feedbackDetail = degreeFeedbackProject.getFeedbackDetail();
            User user = accountManager.getCurUser();
            if (user != null && degreeFeedbackProject != null) {
                String userId = user.getId();
                if (userId.equals(degreeFeedbackProject.getUserId()) || userId.equals("1")) {
                    if (svyName != null && !"".equals(svyName)) {
                        svyName = URLDecoder.decode(svyName, "utf-8");
                        degreeFeedbackProject.setName(svyName);
                    }
                    if (svyNote != null && !"".equals(svyNote)) {
                        svyNote = URLDecoder.decode(svyNote, "utf-8");
                        degreeFeedbackProject.setDescription(svyNote);
                        feedbackDetail.setFeedbackNote(svyNote);
                    }
                    feedbackDetail.setOwnWeight(ownWeight);
                    feedbackDetail.setTopWeight(topWeight);
                    feedbackDetail.setMiddleWeight(middleWeight);
                    feedbackDetail.setBottomWeight(bottomWeight);
                    degreeFeedbackProjectManager.save(degreeFeedbackProject);
                    response.getWriter().write("true");
                }
            }
        }
        return NONE;
    }

    public String importExcel() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        List<FeedbackReview> feedbackReviewList = feedbackReviewManager.findByDemoId(feedbackId);
        if (feedbackReviewList.size() != 0) {
            response.setHeader("Content-Type", "text/plain;charset=UTF-8");
            response.getWriter().write("此模板已经生成相关测评，无法更改相关问题");
            return null;
        } else {
            //将Excel文件上传到服务端
            String desPath = ServletActionContext.getServletContext().getRealPath("/upload");
            File destFile = new File(desPath, someFileName);
            FileUtils.copyFile(some, destFile);
            //解析Excel
            List<DegreeFeedbackItem> degreeFeedbackItemList = degreeFeedbackProjectManager.reader(destFile);
            User user = accountManager.getCurUser();
            DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedbackByUser(feedbackId, user.getId());
            List<Question> questions = questionManager.findDetails(feedbackId, "2");
            if(questions.size()==0){
                response.getWriter().write("请先新建一个测评表格再上传EXCEL文件");
                return null;
            }
            questions.get(0).setDegreeFeedbackItems(degreeFeedbackItemList);
            questionManager.save(questions.get(0));
            degreeFeedbackProject.setQuestions(questions);
            degreeFeedbackProjectManager.save(degreeFeedbackProject);
        }
        return RELOADDESIGN;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public File getSome() {
        return some;
    }

    public void setSome(File some) {
        this.some = some;
    }

    public String getSomeFileName() {
        return someFileName;
    }

    public void setSomeFileName(String someFileName) {
        this.someFileName = someFileName;
    }
}
