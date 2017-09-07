package com.key.dwsurvey.action.nologin;

import com.key.common.QuType;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.*;
import com.key.dwsurvey.service.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jielao on 2017/8/1.
 */
@Namespace("/")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
        @Result(name=FeedbackAction.ANSWERFEEDBACK,location="/WEB-INF/page/content/feedback-review/answer-feedback.jsp",type=Struts2Utils.DISPATCHER),
        @Result(name=FeedbackAction.RELOAD_ANSWER_SUCCESS, location="/WEB-INF/page/content/diaowen-answer/response-success.jsp", type=Struts2Utils.DISPATCHER),
        @Result(name=FeedbackAction.CHECKANSWERFEEDBACK, location = "/WEB-INF/page/content/feedback-review/check-answer-feedback.jsp", type = Struts2Utils.DISPATCHER)
})

@AllowedMethods({"answerFeedback", "checkAnswer"})
public class FeedbackAction extends ActionSupport {

    protected final static String ANSWERFEEDBACK="answerFeedback";
    protected final static String RELOAD_ANSWER_SUCCESS = "reloadAnswerSuccess";
    protected final static String CHECKANSWERFEEDBACK = "checkAnswerFeedback";

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    @Autowired
    private QuestionManager questionManager;

    @Autowired
    private DegreeFeedbackProjectManager degreeFeedbackProjectManager;

    @Autowired
    private FeedbackAnswerManager feedbackAnswerManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private DepartmentManager departmentManager;

    @Autowired
    private ReviewDimensionManager reviewDimensionManager;

    @Autowired
    private AnFeedbackItemManager anFeedbackItemManager;


    private String reviewId;

    //回答问卷
    public String answerFeedback() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        FeedbackReview feedbackReview = feedbackReviewManager.get(reviewId);
        DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedback(feedbackReview.getFeedbackDemoId());
        degreeFeedbackProject.setQuestions(questionManager.findDetails(degreeFeedbackProject.getId(), "2"));
        request.setAttribute("review", feedbackReview);
        request.setAttribute("feedbackProject", degreeFeedbackProject);
        User user = accountManager.getCurUser();
        List<User> examineeList = new ArrayList<>();
        List<ReviewDimension> reviewDimensionList = reviewDimensionManager.findByInvestigate(user.getId(), reviewId);
        for(ReviewDimension reviewDimension: reviewDimensionList){
            //被考核者
            User examinee = userManager.get(reviewDimension.getExaminee());
            if(feedbackAnswerManager.findHaveReviewUser(reviewId, user.getId(), examinee.getId()).size() == 0) {
                examineeList.add(examinee);
            }
        }
        request.setAttribute("examineeList", examineeList);
        request.setAttribute("user", user);
        //获取草稿数据
        List<FeedbackAnswer> feedbackAnswerList = feedbackAnswerManager.findAnswerByAnswerUser(reviewId, user.getId());
        if(feedbackAnswerList.size()!=0) {
            for (FeedbackAnswer feedbackAnswer : feedbackAnswerList) {
                List<AnFeedbackItem> anFeedbackItemList = anFeedbackItemManager.findAnswerDetail(feedbackAnswer.getId());
                for(AnFeedbackItem anFeedbackItem: anFeedbackItemList){
                    request.setAttribute("itemValue_" + feedbackAnswer.getOwnerId() + "_" + anFeedbackItem.getFeedbackRowId(), anFeedbackItem.getAnswerScore());
                }
            }
        }
        return ANSWERFEEDBACK;
    }

    //保存回答信息
    public String save() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        FeedbackReview feedbackReview = feedbackReviewManager.get(reviewId);
        String answerStatus = request.getParameter("answerStatus");
        //获取评价者对被评者的维度
        User user = accountManager.getCurUser();
        List<User> examineeList = new ArrayList<>();
        List<ReviewDimension> reviewDimensionList = reviewDimensionManager.findByInvestigate(user.getId(), reviewId);
        for(ReviewDimension reviewDimension: reviewDimensionList){
            //被考核者
            User examinee = userManager.get(reviewDimension.getExaminee());
            examineeList.add(examinee);
        }
        for(User answerOwner: examineeList) {
            ReviewDimension reviewDimension = reviewDimensionManager.findDetailsByAll(answerOwner.getId(), user.getId(), reviewId);
            FeedbackAnswer entity = new FeedbackAnswer();
            entity.setAnswerUser(user.getId());
            entity.setOwnerId(answerOwner.getId());
            entity.setDimensionId(reviewDimension.getDimensionId());
            entity.setAnswerStatus(Integer.parseInt(answerStatus));
            Map<String, Map<String, Object>> quMaps = new HashMap<String, Map<String, Object>>();
            Map<String, Object> feedbackMaps = WebUtils.getParametersStartingWith(request, "qu_" + answerOwner.getId() + "_");
            for (String key : feedbackMaps.keySet()) {
                String tag = feedbackMaps.get(key).toString();
                Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
                feedbackMaps.put(key, map);
            }
            quMaps.put("feedbackMaps", feedbackMaps);
            entity.setReviewId(reviewId);
            feedbackAnswerManager.saveAnswer(entity, quMaps);
            System.out.println(quMaps);
        }
        return RELOAD_ANSWER_SUCCESS;
    }

    //评价者查看数据
    public String checkAnswer() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        FeedbackReview feedbackReview = feedbackReviewManager.get(reviewId);
        DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedback(feedbackReview.getFeedbackDemoId());
        degreeFeedbackProject.setQuestions(questionManager.findDetails(degreeFeedbackProject.getId(), "2"));
        request.setAttribute("review", feedbackReview);
        request.setAttribute("feedbackProject", degreeFeedbackProject);
        User user = accountManager.getCurUser();
        List<User> examineeList = new ArrayList<>();
        List<FeedbackAnswer> feedbackAnswerList = feedbackAnswerManager.findAnswerByAnswerUser(reviewId, user.getId());
        for(FeedbackAnswer feedbackAnswer: feedbackAnswerList){
            //被考核者
            User examinee = userManager.get(feedbackAnswer.getOwnerId());
            examineeList.add(examinee);
            List<AnFeedbackItem> anFeedbackItemList = anFeedbackItemManager.findAnswerDetail(feedbackAnswer.getId());
            feedbackAnswer.setAnFeedbackItemList(anFeedbackItemList);
        }
        request.setAttribute("examineeList", examineeList);
        Department userDepart = departmentManager.get(user.getDepartment());
        user.setDepartment(userDepart.getName());
        request.setAttribute("user", user);
        request.setAttribute("feedbackAnswerList", feedbackAnswerList);
        return CHECKANSWERFEEDBACK;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
