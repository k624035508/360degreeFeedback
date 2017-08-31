package com.key.dwsurvey.service.impl;

import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.RolesDao;
import com.key.dwsurvey.entity.Roles;
import com.key.dwsurvey.service.RolesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jielao on 2017/8/26.
 */
@Service
public class RolesManagerImpl extends BaseServiceImpl<Roles, String> implements RolesManager {
    @Autowired
    private RolesDao rolesDao;

    @Override
    public void setBaseDao(){ this.baseDao = rolesDao; }

}
