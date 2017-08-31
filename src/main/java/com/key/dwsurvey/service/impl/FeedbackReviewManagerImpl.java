package com.key.dwsurvey.service.impl;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.FeedbackReviewDao;
import com.key.dwsurvey.entity.FeedbackReview;
import com.key.dwsurvey.service.FeedbackReviewManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jielao on 2017/7/31.
 */

@Service("feedbackReviewManager")
public class FeedbackReviewManagerImpl extends BaseServiceImpl<FeedbackReview, String> implements FeedbackReviewManager {

    @Autowired
    private FeedbackReviewDao feedbackReviewDao;

    @Autowired
    private AccountManager accountManager;



    @Override
    public void setBaseDao() { this.baseDao = feedbackReviewDao; }

    public Page<FeedbackReview> findPage(Page<FeedbackReview> page, FeedbackReview entity){
        List<Criterion> criterions=new ArrayList<Criterion>();
        return super.findPageByCri(page, criterions);
    }

    @Transactional
    @Override
    public void save(FeedbackReview f){
        User user = accountManager.getCurUser();
        String userId = f.getUserId();
        String id = f.getId();
        if(id==null){
            f.setUserId(user.getId());
            userId = f.getUserId();
        }
        if(userId!=null && userId.equals(user.getId())){
            feedbackReviewDao.save(f);
        }
    }

    @Override
    public Page<FeedbackReview> findPageByUser(Page<FeedbackReview> page, FeedbackReview entity){
        User user = accountManager.getCurUser();
        if(user!=null && !user.getId().equals("1")) {
            List<Criterion> criterionList = new ArrayList<>();
            criterionList.add(Restrictions.eq("userId", user.getId()));
            page.setOrderBy("createDate");
            page.setOrderDir("desc");
            page = feedbackReviewDao.findPageList(page, criterionList);
        }
        else if(user!=null && user.getId().equals("1")) {
            List<Criterion> criterionList = new ArrayList<>();
            page.setOrderBy("createDate");
            page.setOrderDir("desc");
            page = feedbackReviewDao.findPageList(page, criterionList);
        }
        return page;
    }

    public Page<FeedbackReview> findPageByExaminee(Page<FeedbackReview> page){
        User user = accountManager.getCurUser();
        if(user != null){
            List<Criterion> criterionList = new ArrayList<>();
            criterionList.add(Restrictions.like("examinee", user.getId(), MatchMode.ANYWHERE));
            page.setOrderBy("createDate");
            page.setOrderDir("desc");
            page = feedbackReviewDao.findPageList(page, criterionList);
        }
        return page;
    }

    public List<FeedbackReview> findByDemoId( String feedbackDemoId){
        Page<FeedbackReview> page = new Page<FeedbackReview>();
        User user = accountManager.getCurUser();
        if(user!=null) {
            List<Criterion> criterionList = new ArrayList<>();
            criterionList.add(Restrictions.eq("userId", user.getId()));
            criterionList.add(Restrictions.eq("feedbackDemoId", feedbackDemoId));
            page = feedbackReviewDao.findPageList(page, criterionList);
        }
        return page.getResult();
    }

}
