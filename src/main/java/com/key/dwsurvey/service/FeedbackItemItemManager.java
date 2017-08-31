package com.key.dwsurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.DegreeFeedbackItemItem;

import java.util.List;

/**
 * 评测指标考核项目
 * Created by jielao on 2017/7/27.
 */
public interface FeedbackItemItemManager extends BaseService<DegreeFeedbackItemItem, String> {

    public List<DegreeFeedbackItemItem> findByItemId(String itemId);

    public DegreeFeedbackItemItem upOptionName(String itemId, String itemItemId, String itemName);

    public void ajaxDelete(String itemItemId);
}
