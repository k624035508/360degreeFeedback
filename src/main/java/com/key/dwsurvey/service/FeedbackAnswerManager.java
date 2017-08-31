package com.key.dwsurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.FeedbackAnswer;

import java.util.List;
import java.util.Map;

/**
 * 考核回答
 * Created by jielao on 2017/8/2.
 */
public interface FeedbackAnswerManager extends BaseService<FeedbackAnswer, String> {

    public void saveAnswer(FeedbackAnswer feedbackAnswer, Map<String, Map<String, Object>> quMaps);

    public List<FeedbackAnswer> findAnswerListByReview(String reviewId);

    public List<FeedbackAnswer> findAnswerByAnswerUser(String reviewId, String answerUser);

    public List<FeedbackAnswer> findHaveReviewUser(String reviewId, String answerUser, String ownerId);

    public List<FeedbackAnswer> findReviewOwnerAndDimension(String reviewId, String ownerId, String dimensionId);

    public String exportXLS(String reviewId, String examineeId, String savePath) throws Exception;

}
