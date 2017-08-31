package com.key.dwsurvey.dao.impl;

import com.key.common.dao.BaseDaoImpl;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.dwsurvey.dao.DegreeFeedbackProjectDao;
import com.key.dwsurvey.entity.DegreeFeedbackProject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jielao on 2017/7/20.
 */
@Repository
public class DegreeFeedbackProjectDaoImpl extends BaseDaoImpl<DegreeFeedbackProject, String> implements DegreeFeedbackProjectDao {

    public List<DegreeFeedbackProject> findByUserId(String userId){
        Page<DegreeFeedbackProject> page = new Page<DegreeFeedbackProject>();
        List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter("EQS_userId", userId));
        return findAll(page, filters);
    }
}
