package com.key.dwsurvey.service.impl;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.ReviewDimensionDao;
import com.key.dwsurvey.entity.ReviewDimension;
import com.key.dwsurvey.service.ReviewDimensionManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jielao on 2017/8/8.
 */
@Service
public class ReviewDimensionManagerImpl extends BaseServiceImpl<ReviewDimension, String> implements ReviewDimensionManager {

    @Autowired
    private ReviewDimensionDao reviewDimensionDao;

    @Override
    public void setBaseDao() { this.baseDao = reviewDimensionDao; }

    public List<ReviewDimension> findDetails(String reviewId){
        Page<ReviewDimension> page = new Page<ReviewDimension>();
        List<Criterion> criterions=new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("reviewId", reviewId));
        return reviewDimensionDao.findPageList(page, criterions).getResult();
    }

    public List<ReviewDimension> findDetailsByOther(String reviewId, String examinee){
        Page<ReviewDimension> page = new Page<ReviewDimension>();
        List<Criterion> criterions=new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("reviewId", reviewId));
        criterions.add(Restrictions.eq("examinee", examinee));
        criterions.add(Restrictions.ne("dimensionId", "1"));
        return reviewDimensionDao.findPageList(page, criterions).getResult();
    }

    public List<ReviewDimension> findDetailsByHaveOwn(String reviewId, String examinee){
        Page<ReviewDimension> page = new Page<ReviewDimension>();
        List<Criterion> criterions=new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("reviewId", reviewId));
        criterions.add(Restrictions.eq("examinee", examinee));
        return reviewDimensionDao.findPageList(page, criterions).getResult();
    }

    public ReviewDimension findByReview(String reviewId, String dimensionId){
        List<Criterion> criterions=new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("reviewId", reviewId));
        criterions.add(Restrictions.eq("dimensionId", dimensionId));
        return reviewDimensionDao.findFirst(criterions);
    }

    public List<ReviewDimension> findByInvestigateList(String userId){
        Page<ReviewDimension> page = new Page<ReviewDimension>();
        List<Criterion> criterions=new ArrayList<Criterion>();
        criterions.add(Restrictions.like("investigate", userId, MatchMode.ANYWHERE));
        return reviewDimensionDao.findPageList(page, criterions).getResult();
    }

    public List<ReviewDimension> findByInvestigate(String userId, String reviewId){
        Page<ReviewDimension> page = new Page<ReviewDimension>();
        List<Criterion> criterions=new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("reviewId", reviewId));
        criterions.add(Restrictions.like("investigate", userId, MatchMode.ANYWHERE));
        return reviewDimensionDao.findPageList(page, criterions).getResult();
    }

    public ReviewDimension findDetailsByAll(String examinee, String investigate, String reviewId){
        List<Criterion> criterions=new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("reviewId", reviewId));
        criterions.add(Restrictions.eq("examinee", examinee));
        criterions.add(Restrictions.like("investigate", investigate, MatchMode.ANYWHERE));
        return reviewDimensionDao.findFirst(criterions);
    }
}
