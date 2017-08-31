package com.key.dwsurvey.dao.impl;

import com.key.common.dao.BaseDaoImpl;
import com.key.dwsurvey.dao.FeedbackReviewDao;
import com.key.dwsurvey.entity.FeedbackReview;
import com.key.dwsurvey.entity.ReviewDimension;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jielao on 2017/7/31.
 */

@Repository
public class FeedbackReviewDaoImpl extends BaseDaoImpl<FeedbackReview, String> implements FeedbackReviewDao {

    @Override
    public void save(FeedbackReview entity){
        Session session=getSession();
        session.saveOrUpdate(entity);
        List<ReviewDimension> reviewDimensionList = entity.getReviewDimensionList();
        if(reviewDimensionList.size() != 0) {
            for (ReviewDimension reviewDimension : reviewDimensionList) {
                reviewDimension.setReviewId(entity.getId());
                session.saveOrUpdate(reviewDimension);
            }
        }
    }
}
