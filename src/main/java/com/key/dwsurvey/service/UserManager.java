package com.key.dwsurvey.service;

import com.key.common.base.entity.User;
import com.key.common.plugs.page.Page;
import com.key.common.service.BaseService;

import java.util.List;

public interface UserManager extends BaseService<User, String>{

	public void adminSave(User entity, String[] userRoleIds);

	public Page<User> findPage(Page<User> page, User entity);

	public List<User> findUserListByDepartment(String departmentId);

	public void disUser(String id);

	public User findNameUn(String id, String loginName);

	public User findNicknameUn(String id, String name);

	public User findEmailUn(String id, String email);

	public User findByCode(String code);

	public User findByActivationCode(String code);

	public void resetUserDepartment(String departmentId);

	public User findByUserName(String userName);

}
