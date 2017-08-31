package com.key.dwsurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.DegreeFeedbackItem;

import java.util.List;

/**
 * 评测选项
 * Created by jielao on 2017/7/24.
 */
public interface FeedbackItemManager extends BaseService<DegreeFeedbackItem, String> {

    public List<DegreeFeedbackItem> findByQuId(String quId);

    public DegreeFeedbackItem upOptionName(String quId, String quItemId, String optionName);

    public void ajaxDelete(String quItemId);
}
