package com.key.dwsurvey.action;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.FeedbackAnswer;
import com.key.dwsurvey.entity.FeedbackReview;
import com.key.dwsurvey.entity.ReviewDimension;
import com.key.dwsurvey.service.FeedbackAnswerManager;
import com.key.dwsurvey.service.FeedbackReviewManager;
import com.key.dwsurvey.service.ReviewDimensionManager;
import com.key.dwsurvey.service.UserManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 我的待评考核
 * Created by jielao on 2017/8/3.
 */
@Namespace("/")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
        @Result(name=ActionSupport.SUCCESS, location = "/WEB-INF/page/content/feedback-review/pend-evaluate.jsp",type = Struts2Utils.DISPATCHER),
        @Result(name=MyPendEvaluateAction.VIEWEVALUATE, location = "/WEB-INF/page/content/feedback-review/have-pend-evaluate.jsp", type = Struts2Utils.DISPATCHER)
})
@AllowedMethods({"haveEvaluate"})
public class MyPendEvaluateAction extends ActionSupport {
    protected final static String VIEWEVALUATE = "viewevaluate";

    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private FeedbackAnswerManager feedbackAnswerManager;

    @Autowired
    private ReviewDimensionManager reviewDimensionManager;

    @Autowired
    private UserManager userManager;

    @Override
    public String execute() throws Exception{
        //我需要完成的测评
        HttpServletRequest request = Struts2Utils.getRequest();
        User user = accountManager.getCurUser();
        List<FeedbackReview> feedbackReviewListWithDuplicate = new ArrayList<FeedbackReview>();
        List<ReviewDimension> reviewDimensionList = reviewDimensionManager.findByInvestigateList(user.getId());
        for (ReviewDimension reviewDimension: reviewDimensionList){
            FeedbackReview feedbackReview = new FeedbackReview();
            feedbackReview = feedbackReviewManager.getModel(reviewDimension.getReviewId());
            feedbackReviewListWithDuplicate.add(feedbackReview);
        };
        //剔除重复的考核表
        LinkedHashSet<FeedbackReview> set = new LinkedHashSet(feedbackReviewListWithDuplicate);
        List<FeedbackReview> feedbackReviewListNeedAnswer = new ArrayList<FeedbackReview>(set);  //需要评价的评价表
        //储存进新的List
        List<FeedbackReview> newFeedbackReviewList = new ArrayList<FeedbackReview>();
        //删除已评完分的考核表
        for(FeedbackReview feedbackReviewWithoutDuplicate: feedbackReviewListNeedAnswer){
            List<ReviewDimension> reviewDimensionListHaveAnswer = reviewDimensionManager.findByInvestigate(user.getId(), feedbackReviewWithoutDuplicate.getId());
            List<User> examineeList = new ArrayList<>();
            for(ReviewDimension reviewDimension: reviewDimensionListHaveAnswer){
                //被考核者
                User examinee = userManager.get(reviewDimension.getExaminee());
                if(feedbackAnswerManager.findHaveReviewUser(feedbackReviewWithoutDuplicate.getId(), user.getId(), examinee.getId()).size() == 0) {
                    examineeList.add(examinee);
                }
            }
            if (examineeList.size() != 0){
                newFeedbackReviewList.add(feedbackReviewWithoutDuplicate);
            }
        }
        request.getSession().setAttribute("list", newFeedbackReviewList);
        return SUCCESS;
    }

    public String haveEvaluate() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        User user = accountManager.getCurUser();
        List<FeedbackReview> feedbackReviewListWithDuplicate = new ArrayList<FeedbackReview>();
        List<ReviewDimension> reviewDimensionList = reviewDimensionManager.findByInvestigateList(user.getId());
        for (ReviewDimension reviewDimension: reviewDimensionList){
            FeedbackReview feedbackReview = new FeedbackReview();
            feedbackReview = feedbackReviewManager.getModel(reviewDimension.getReviewId());
            feedbackReviewListWithDuplicate.add(feedbackReview);
        };
        //剔除重复的考核表
        LinkedHashSet<FeedbackReview> set = new LinkedHashSet(feedbackReviewListWithDuplicate);
        List<FeedbackReview> feedbackReviewListHaveAnswer = new ArrayList<FeedbackReview>(set);  //评价完的评价表
        //储存进新的List
        List<FeedbackReview> newFeedbackReviewList = new ArrayList<FeedbackReview>();
        //获取已评完分的考核表
        for(FeedbackReview feedbackReviewWithoutDuplicate: feedbackReviewListHaveAnswer){
            List<ReviewDimension> reviewDimensionListHaveAnswer = reviewDimensionManager.findByInvestigate(user.getId(), feedbackReviewWithoutDuplicate.getId());
            List<User> examineeList = new ArrayList<>();
            for(ReviewDimension reviewDimension: reviewDimensionListHaveAnswer){
                //被考核者
                User examinee = userManager.get(reviewDimension.getExaminee());
                if(feedbackAnswerManager.findHaveReviewUser(feedbackReviewWithoutDuplicate.getId(), user.getId(), examinee.getId()).size() != 0) {
                    examineeList.add(examinee);
                }
            }
            if (examineeList.size() == reviewDimensionListHaveAnswer.size()){
                newFeedbackReviewList.add(feedbackReviewWithoutDuplicate);
            }
        }
        request.getSession().setAttribute("list", newFeedbackReviewList);
        return VIEWEVALUATE;
    }
}
