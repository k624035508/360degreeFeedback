package com.key.dwsurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.entity.DegreeFeedbackProject;

import java.util.List;

/**
 * Created by jielao on 2017/7/20.
 */
public interface DegreeFeedbackProjectDao extends BaseDao<DegreeFeedbackProject, String> {

    public List<DegreeFeedbackProject> findByUserId(String userId);

}
