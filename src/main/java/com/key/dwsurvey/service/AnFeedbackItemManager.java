package com.key.dwsurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnFeedbackItem;

import java.util.List;

/**
 * Created by jielao on 2017/8/9.
 */
public interface AnFeedbackItemManager extends BaseService<AnFeedbackItem, String> {

    public List<AnFeedbackItem> findAnswerDetail(String belongAnswerId);

    public List<AnFeedbackItem> findFeedbackItemAnswer(String belongAnswerId, String feedbackRowId);

}
