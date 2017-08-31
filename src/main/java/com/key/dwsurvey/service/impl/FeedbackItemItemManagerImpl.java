package com.key.dwsurvey.service.impl;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.FeedbackItemItemDao;
import com.key.dwsurvey.entity.DegreeFeedbackItemItem;
import com.key.dwsurvey.service.FeedbackItemItemManager;
import com.key.dwsurvey.service.FeedbackItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jielao on 2017/7/27.
 */
@Service
public class FeedbackItemItemManagerImpl extends BaseServiceImpl<DegreeFeedbackItemItem, String> implements FeedbackItemItemManager {

    @Autowired
    private FeedbackItemItemDao feedbackItemItemDao;

    @Override
    public void setBaseDao(){ this.baseDao = feedbackItemItemDao; }

    public List<DegreeFeedbackItemItem> findByItemId(String itemId){
        Page<DegreeFeedbackItemItem> page = new Page<DegreeFeedbackItemItem>();
        page.setOrderBy("orderById");
        page.setOrderDir("asc");

        List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter("EQS_itemId", itemId));
        filters.add(new PropertyFilter("EQI_visibility", "1"));
        return findAll(page, filters);
    }

    @Override
    @Transactional
    public DegreeFeedbackItemItem upOptionName(String itemId, String itemItemId, String itemName) {
        DegreeFeedbackItemItem degreeFeedbackItemItem = feedbackItemItemDao.get(itemItemId);
        degreeFeedbackItemItem.setName(itemName);
        feedbackItemItemDao.save(degreeFeedbackItemItem);
        return degreeFeedbackItemItem;
    }

    @Override
    @Transactional
    public void ajaxDelete(String itemItemId){
        DegreeFeedbackItemItem degreeFeedbackItemItem = feedbackItemItemDao.get(itemItemId);
        degreeFeedbackItemItem.setVisibility(0);
        feedbackItemItemDao.save(degreeFeedbackItemItem);
    }

}
