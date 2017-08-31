package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.FeedbackAnswer;

import java.util.Map;

/**
 * Created by jielao on 2017/8/2.
 */

public interface FeedbackAnswerDao extends BaseDao<FeedbackAnswer, String> {

    public void saveAnswer(FeedbackAnswer feedbackAnswer, Map<String, Map<String, Object>> quMaps);

}
