package com.key.dwsurvey.action;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.*;
import com.key.dwsurvey.service.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jielao on 2017/8/23.
 */

@Namespaces({@Namespace("/statistics")})
@InterceptorRefs({@InterceptorRef("paramsPrepareParamsStack")})
@Results({
        @Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/page/content/feedback-statistics/statistics-list.jsp", type = Struts2Utils.DISPATCHER),
        @Result(name = FeedbackStatisticsAction.VIEWREPORT, location = "/WEB-INF/page/content/feedback-statistics/statistics-review.jsp", type = Struts2Utils.DISPATCHER),
        @Result(name = FeedbackStatisticsAction.DETAILREPORT, location = "/WEB-INF/page/content/feedback-review/report-detail.jsp", type = Struts2Utils.DISPATCHER)
})
@AllowedMethods({"viewStatistics", "personalReport"})
public class FeedbackStatisticsAction extends ActionSupport {
    protected final static String VIEWREPORT = "viewReport";
    protected final static String DETAILREPORT = "detailReport";

    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private FeedbackDimensionManager feedbackDimensionManager;

    @Autowired
    private ReviewDimensionManager reviewDimensionManager;

    @Autowired
    private DegreeFeedbackProjectManager degreeFeedbackProjectManager;

    @Autowired
    private QuestionManager questionManager;

    @Autowired
    private FeedbackAnswerManager feedbackAnswerManager;

    @Autowired
    private AnFeedbackItemManager anFeedbackItemManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private DepartmentManager departmentManager;

    private String reviewId;

    @Override
    public String execute() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        Page<FeedbackReview> page = new Page<FeedbackReview>();
        FeedbackReview entity = new FeedbackReview();
        page = feedbackReviewManager.findPageByUser(page, entity);
        request.setAttribute("page", page);
        return SUCCESS;
    }

    public String viewStatistics() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        List<FeedbackAnswer> feedbackAnswerList = new ArrayList<>();
        List<FeedbackDimension> feedbackDimensionList = feedbackDimensionManager.findAll(new Page<FeedbackDimension>(), new ArrayList<PropertyFilter>());
        List<TempScore> tempScoreList = new ArrayList<>();
        FeedbackReview feedbackReview = feedbackReviewManager.get(reviewId);
        List<User> userList = new ArrayList<>();
        String[] userIds = feedbackReview.getExaminee().split("&");
        for(String userId: userIds){
            userList.add(userManager.get(userId));
        }
        DegreeFeedbackProject degreeFeedbackProject = degreeFeedbackProjectManager.getFeedback(feedbackReview.getFeedbackDemoId());
        degreeFeedbackProject.setQuestions(questionManager.findDetails(degreeFeedbackProject.getId(), "2"));
        for(User user: userList) {
            for (FeedbackDimension feedbackDimension : feedbackDimensionList) {
                feedbackAnswerList = feedbackAnswerManager.findReviewOwnerAndDimension(reviewId, user.getId(), feedbackDimension.getId());
                for (DegreeFeedbackItem degreeFeedbackItem : degreeFeedbackProject.getQuestions().get(0).getDegreeFeedbackItems()) {
                    for (DegreeFeedbackItemItem degreeFeedbackItemItem : degreeFeedbackItem.getItemList()) {
                        //scores: 一个维度里的平均分
                        Float scores = 0f;
                        for (FeedbackAnswer feedbackAnswer : feedbackAnswerList) {
                            AnFeedbackItem anFeedbackItem = anFeedbackItemManager.findFeedbackItemAnswer(feedbackAnswer.getId(), degreeFeedbackItemItem.getId()).get(0);
                            scores += Integer.parseInt(anFeedbackItem.getAnswerScore());
                        }
                        scores = scores / feedbackAnswerList.size();

                        if (!scores.isNaN()) {
                            TempScore tempScore = new TempScore();
                            tempScore.setUserId(user.getId());
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
                avgScores = avgScores/userList.size();
                degreeFeedbackItemItem.setAvgScore(avgScores * 100 /100);
            }
        }
        //新建TempScore储存平均数据
        List<TempScore> tempScoreListWithUser = new ArrayList<>();
        //获取总分
        List<TempScore> tempScoreTotalScores = new ArrayList<>();
        //全部用户平均分
        float allUserTotalAvgScore = 0f;
        for(User user: userList) {
            //单个用户的总分
            TempScore tempScoreUserScore = new TempScore();
            float userTotalScore = 0f;
            for (DegreeFeedbackItem degreeFeedbackItem : degreeFeedbackProject.getQuestions().get(0).getDegreeFeedbackItems()) {
                for (DegreeFeedbackItemItem degreeFeedbackItemItem : degreeFeedbackItem.getItemList()) {
                    float avgScores = 0f;
                    for(TempScore tempScore: tempScoreList){
                        if(tempScore.getItemItemId().equals(degreeFeedbackItemItem.getId()) && tempScore.getUserId().equals(user.getId())){
                            Float weight = Float.parseFloat(tempScore.getWeight());
                            avgScores += tempScore.getAvgScores() * weight / 100;
                            userTotalScore += tempScore.getAvgScores() * weight / 100;
                            allUserTotalAvgScore += tempScore.getAvgScores() * weight / 100;
                        }
                    }
                    TempScore tempScoreWithUser = new TempScore();
                    tempScoreWithUser.setUserId(user.getId());
                    tempScoreWithUser.setAvgScores(avgScores);
                    tempScoreWithUser.setItemItemId(degreeFeedbackItemItem.getId());
                    tempScoreListWithUser.add(tempScoreWithUser);
                }
            }
            tempScoreUserScore.setUserId(user.getId());
            tempScoreUserScore.setAvgScores(userTotalScore);
            tempScoreTotalScores.add(tempScoreUserScore);
        }
        String baseUrl = "";
        baseUrl = request.getScheme() +"://" + request.getServerName()
                + (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
                + request.getContextPath();

        request.setAttribute("baseUrl", baseUrl);
        request.setAttribute("review", feedbackReview);
        request.setAttribute("feedbackProject", degreeFeedbackProject);
        request.setAttribute("tempScoreList", tempScoreListWithUser);
        request.setAttribute("tempScoreTotalScores", tempScoreTotalScores);
        request.setAttribute("allUserTotalAvgScore", allUserTotalAvgScore/userList.size());
        request.setAttribute("userList", userList);
        request.setAttribute("feedbackDimensionList", feedbackDimensionList);
        return VIEWREPORT;
    }

    public String personalReport() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        String userId = request.getParameter("userId");
        User user = userManager.get(userId);
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
        return DETAILREPORT;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
