package com.key.dwsurvey.service.impl;

import com.key.common.plugs.page.Page;
import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.AnFeedbackItemDao;
import com.key.dwsurvey.entity.AnFeedbackItem;
import com.key.dwsurvey.service.AnFeedbackItemManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jielao on 2017/8/9.
 */
@Service
public class AnFeedbackItemManagerImpl extends BaseServiceImpl<AnFeedbackItem, String> implements AnFeedbackItemManager {

    @Autowired
    private AnFeedbackItemDao anFeedbackItemDao;

    @Override
    public void setBaseDao() { this.baseDao = anFeedbackItemDao; }

    public List<AnFeedbackItem> findAnswerDetail(String belongAnswerId){
        Criterion criterion = Restrictions.eq("belongAnswerId", belongAnswerId);
        return anFeedbackItemDao.find(criterion);
    }

    public List<AnFeedbackItem> findFeedbackItemAnswer(String belongAnswerId, String feedbackRowId){
        Page<AnFeedbackItem> page = new Page<AnFeedbackItem>();
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("belongAnswerId", belongAnswerId));
        criterionList.add(Restrictions.eq("feedbackRowId", feedbackRowId));
        page = anFeedbackItemDao.findPageList(page, criterionList);
        return page.getResult();
    }
}
