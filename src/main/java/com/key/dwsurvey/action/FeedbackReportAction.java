package com.key.dwsurvey.action;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.*;
import com.key.dwsurvey.service.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 测评报告
 * Created by jielao on 2017/8/9.
 */
@Namespaces({@Namespace("/report")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
        @Result(name= CrudActionSupport.SUCCESS, location = "/WEB-INF/page/content/feedback-review/report-list.jsp", type = Struts2Utils.DISPATCHER),
        @Result(name = FeedbackReportAction.REPORT, location = "/WEB-INF/page/content/feedback-review/report-detail.jsp", type = Struts2Utils.DISPATCHER)
})
@AllowedMethods({"report", "downloadExcel"})
public class FeedbackReportAction extends ActionSupport {
    protected final static String REPORT = "report";

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private DepartmentManager departmentManager;

    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    @Autowired
    private FeedbackDimensionManager feedbackDimensionManager;

    @Autowired
    private FeedbackAnswerManager feedbackAnswerManager;

    @Autowired
    private DegreeFeedbackProjectManager degreeFeedbackProjectManager;

    @Autowired
    private QuestionManager questionManager;

    @Autowired
    private AnFeedbackItemManager anFeedbackItemManager;

    @Autowired
    private ReviewDimensionManager reviewDimensionManager;

    private String reviewId;

    @Override
    public String execute() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        Page<FeedbackReview> page = new Page<FeedbackReview>();
        page = feedbackReviewManager.findPageByExaminee(page);
        request.setAttribute("page", page);
        return SUCCESS;
    }

    public String report() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        User user = accountManager.getCurUser();
        List<FeedbackAnswer> feedbackAnswerList = new ArrayList<>();
        List<FeedbackDimension> feedbackDimensionList = feedbackDimensionManager.findAll(new Page<FeedbackDimension>(), new ArrayList<PropertyFilter>());
        List<TempScore> tempScoreList = new ArrayList<>();
        FeedbackReview feedbackReview = feedbackReviewManager.get(reviewId);
        List<ReviewDimension> reviewDimensionList = reviewDimensionManager.findDetailsByHaveOwn(reviewId, user.getId());
        DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedback(feedbackReview.getFeedbackDemoId());
        degreeFeedbackProject.setQuestions(questionManager.findDetails(degreeFeedbackProject.getId(), "2"));
        for(FeedbackDimension feedbackDimension: feedbackDimensionList) {
            feedbackAnswerList = feedbackAnswerManager.findReviewOwnerAndDimension(reviewId, user.getId(), feedbackDimension.getId());
            for(DegreeFeedbackItem degreeFeedbackItem: degreeFeedbackProject.getQuestions().get(0).getDegreeFeedbackItems()) {
                for(DegreeFeedbackItemItem degreeFeedbackItemItem: degreeFeedbackItem.getItemList()) {
                    //scores: 一个维度里的平均分
                    Float scores = 0f;
                    for (FeedbackAnswer feedbackAnswer : feedbackAnswerList) {
                        AnFeedbackItem anFeedbackItem = anFeedbackItemManager.findFeedbackItemAnswer(feedbackAnswer.getId(), degreeFeedbackItemItem.getId()).get(0);
                        scores += Integer.parseInt(anFeedbackItem.getAnswerScore());
                    }
                    scores = scores / feedbackAnswerList.size();

                    if(!scores.isNaN()) {
                        TempScore tempScore = new TempScore();
                        tempScore.setDimensionId(feedbackDimension.getId());
                        tempScore.setItemItemId(degreeFeedbackItemItem.getId());
                        tempScore.setAvgScores(scores);
                        ReviewDimension reviewDimensionWeight = reviewDimensionManager.findByReview(reviewId, feedbackDimension.getId());
                        tempScore.setWeight(reviewDimensionWeight.getWeight());
                        tempScoreList.add(tempScore);
                    }
                }
            }
        }
        for(DegreeFeedbackItem degreeFeedbackItem: degreeFeedbackProject.getQuestions().get(0).getDegreeFeedbackItems()) {
            for(DegreeFeedbackItemItem degreeFeedbackItemItem: degreeFeedbackItem.getItemList()) {
                //所有维度加权平均
                float avgScores = 0f;
                for(TempScore tempScore: tempScoreList){
                    if(tempScore.getItemItemId().equals(degreeFeedbackItemItem.getId())){
                        Float weight = Float.parseFloat(tempScore.getWeight());
                        avgScores += tempScore.getAvgScores() * weight / 100;
                    }
                }

                degreeFeedbackItemItem.setAvgScore(avgScores * 100 /100);
            }
        }
        List<Department> departmentList = departmentManager.findList(new ArrayList<>());
        request.setAttribute("departmentList", departmentList);
        request.setAttribute("feedbackAnswerList", feedbackAnswerList);
        request.setAttribute("user", user);
        request.setAttribute("review", feedbackReview);
        request.setAttribute("feedbackProject", degreeFeedbackProject);
        request.setAttribute("tempScoreList", tempScoreList);
        request.setAttribute("feedbackDimensionList", feedbackDimensionList);
        request.setAttribute("reviewDimensionList", reviewDimensionList);
        return REPORT;
    }

    public String downloadExcel() throws Exception{
        HttpServletRequest request=Struts2Utils.getRequest();
        HttpServletResponse response=Struts2Utils.getResponse();
        try{
            String savePath = request.getSession().getServletContext().getRealPath("/");
            String downloadUserId = "";
            User user = accountManager.getCurUser();
            if (user.getId().equals("1")){
                downloadUserId = request.getParameter("userId");
            } else {
                downloadUserId = user.getId();
            }
            savePath = feedbackAnswerManager.exportXLS(reviewId, downloadUserId, savePath);
            response.sendRedirect(request.getContextPath()+savePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
