package com.key.dwsurvey.service.impl;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseService;
import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.FeedbackItemDao;
import com.key.dwsurvey.entity.DegreeFeedbackItem;
import com.key.dwsurvey.service.FeedbackItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jielao on 2017/7/24.
 */

@Service
public class FeedbackItemManagerImpl extends BaseServiceImpl<DegreeFeedbackItem, String> implements FeedbackItemManager {

    @Autowired
    private FeedbackItemDao feedbackItemDao;

    @Override
    public void setBaseDao(){ this.baseDao = feedbackItemDao; }

    public List<DegreeFeedbackItem> findByQuId(String quId){
        Page<DegreeFeedbackItem> page = new Page<DegreeFeedbackItem>();
        page.setOrderBy("orderById");
        page.setOrderDir("asc");

        List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter("EQS_quId", quId));
        filters.add(new PropertyFilter("EQI_visibility", "1"));
        return findAll(page, filters);
    }

    @Override
    @Transactional
    public DegreeFeedbackItem upOptionName(String quId, String quItemId, String optionName){
        DegreeFeedbackItem degreeFeedbackItem = feedbackItemDao.get(quItemId);
        degreeFeedbackItem.setName(optionName);
        feedbackItemDao.save(degreeFeedbackItem);
        return degreeFeedbackItem;
    }

    @Override
    @Transactional
    public void ajaxDelete(String quItemId){
        DegreeFeedbackItem degreeFeedbackItem = feedbackItemDao.get(quItemId);
        degreeFeedbackItem.setVisibility(0);
        feedbackItemDao.save(degreeFeedbackItem);
    }

}
