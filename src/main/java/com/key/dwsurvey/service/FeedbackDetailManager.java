package com.key.dwsurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.FeedbackDetail;

/**
 * Created by jielao on 2017/8/29.
 */
public interface FeedbackDetailManager extends BaseService<FeedbackDetail, String> {

    public FeedbackDetail getByFeedbackId(String feedbackId);

}
