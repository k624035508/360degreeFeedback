package com.key.dwsurvey.service.impl;

import com.key.common.service.BaseServiceImpl;
import com.key.dwsurvey.dao.DepartmentDao;
import com.key.dwsurvey.entity.Department;
import com.key.dwsurvey.service.DepartmentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jielao on 2017/7/31.
 */
@Service
public class DepartmentManagerImpl extends BaseServiceImpl<Department, String> implements DepartmentManager {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void setBaseDao(){ this.baseDao = departmentDao; }
}
