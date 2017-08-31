package com.key.dwsurvey.service;

import com.key.common.plugs.page.Page;
import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.FeedbackReview;

import java.util.List;

/**
 * Created by jielao on 2017/7/31.
 */

public interface FeedbackReviewManager extends BaseService<FeedbackReview, String> {

    public Page<FeedbackReview> findPage(Page<FeedbackReview> page, FeedbackReview entity);

    public Page<FeedbackReview> findPageByUser(Page<FeedbackReview> page, FeedbackReview entity);

    public Page<FeedbackReview> findPageByExaminee(Page<FeedbackReview> page);

    public List<FeedbackReview> findByDemoId(String feedbackDemoId);

}
