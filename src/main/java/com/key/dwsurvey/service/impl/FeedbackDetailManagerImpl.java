package com.key.dwsurvey.service.impl;

import com.key.common.service.BaseServiceImpl;
import com.key.common.utils.ReflectionUtils;
import com.key.dwsurvey.dao.FeedbackDetailDao;
import com.key.dwsurvey.entity.FeedbackDetail;
import com.key.dwsurvey.service.FeedbackDetailManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jielao on 2017/8/29.
 */
@Service
public class FeedbackDetailManagerImpl extends BaseServiceImpl<FeedbackDetail, String> implements FeedbackDetailManager {
    @Autowired
    private FeedbackDetailDao feedbackDetailDao;

    @Override
    public void setBaseDao() { this.baseDao = feedbackDetailDao; }

    @Transactional
    @Override
    public void save(FeedbackDetail f){
        //判断有无，有则更新
        FeedbackDetail feedbackDetail = findUn(f.getDirId());
        if(feedbackDetail == null){
            feedbackDetail = new FeedbackDetail();
        }
        ReflectionUtils.copyAttr(f, feedbackDetail);
        super.save(feedbackDetail);
    }


    public FeedbackDetail findUn(String dirId){
        Criterion criterion= Restrictions.eq("dirId", dirId);
        List<FeedbackDetail> details = feedbackDetailDao.find(criterion);
        if(details!=null && details.size()>0){
            return details.get(0);
        }
        return null;
    }

    public FeedbackDetail getByFeedbackId(String feedbackId){
        return findUn(feedbackId);
    }
}
