package com.key.dwsurvey.dao.impl;

import com.key.common.dao.BaseDaoImpl;
import com.key.dwsurvey.dao.FeedbackAnswerDao;
import com.key.dwsurvey.entity.AnFeedbackItem;
import com.key.dwsurvey.entity.FeedbackAnswer;
import com.key.dwsurvey.entity.FeedbackReview;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * 测评考核回答
 * Created by jielao on 2017/8/2.
 */

@Repository
public class FeedbackAnswerDaoImpl extends BaseDaoImpl<FeedbackAnswer, String> implements FeedbackAnswerDao {

    @Override
    public void saveAnswer(FeedbackAnswer feedbackAnswer, Map<String, Map<String, Object>> quMaps){
        Date curDate = new Date();
        Session session=this.getSession();
        feedbackAnswer.setAnswerDate(curDate);
        session.save(feedbackAnswer);
        //保存答案信息
        String reviewId = feedbackAnswer.getReviewId();
        String feedbackAnswerId = feedbackAnswer.getId();
        FeedbackReview feedbackReview = (FeedbackReview) session.get(FeedbackReview.class, reviewId);
        Map<String,Object> feedbackMaps = quMaps.get("feedbackMaps");
        /**
         * 保存评分题
         * @param feedbackAnswer
         * @param feedbackMaps
         * @param session
         */
        if(feedbackMaps != null){
            for(String key: feedbackMaps.keySet()) {
                Map<String,Object> mapRows=(Map<String, Object>) feedbackMaps.get(key);
                for (String keyRow : mapRows.keySet()) {
                    String rowId=keyRow;
                    String scoreValue=mapRows.get(keyRow).toString();
                    AnFeedbackItem anFeedbackItem=new AnFeedbackItem(feedbackAnswerId,rowId,scoreValue);
                    session.save(anFeedbackItem);
                }
            }
        }
    }

}
