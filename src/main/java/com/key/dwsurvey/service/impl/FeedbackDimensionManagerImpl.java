package com.key.dwsurvey.service.impl;

import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.FeedbackDimensionDao;
import com.key.dwsurvey.entity.FeedbackDimension;
import com.key.dwsurvey.service.FeedbackDimensionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jielao on 2017/8/4.
 */
@Service
public class FeedbackDimensionManagerImpl extends BaseServiceImpl<FeedbackDimension, String> implements FeedbackDimensionManager {
    @Autowired
    private FeedbackDimensionDao feedbackDimensionDao;

    @Override
    public void setBaseDao(){ this.baseDao = feedbackDimensionDao; }

}
