package com.key.dwsurvey.action;


import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.Department;
import com.key.dwsurvey.service.DepartmentManager;
import com.key.dwsurvey.service.UserManager;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Namespaces({@Namespace("/sy/user"),@Namespace("/sy/user/nosm")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
	@Result(name= CrudActionSupport.SUCCESS,location="/WEB-INF/page/content/diaowen-useradmin/list.jsp",type= Struts2Utils.DISPATCHER),
	@Result(name= CrudActionSupport.INPUT,location="/WEB-INF/page/content/diaowen-useradmin/input.jsp",type= Struts2Utils.DISPATCHER),
	@Result(name= CrudActionSupport.RELOAD,location="/sy/user/user-admin.action",type= Struts2Utils.REDIRECT)
})
@AllowedMethods({"checkLoginNamelUn","checkEmailUn"})
public class UserAdminAction extends CrudActionSupport<User, String> {

	@Autowired
	private UserManager userManager;

	@Autowired
	private DepartmentManager departmentManager;

	@Override
	public String list() throws Exception {
		try{
			page=userManager.findPage(page,entity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		HttpServletRequest request= Struts2Utils.getRequest();
		Page<Department> pageDepartment = new Page<Department>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<Department> departmentList =  departmentManager.findAll(pageDepartment, filters);
		request.getSession().setAttribute("departmentList", departmentList);
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		HttpServletRequest request= Struts2Utils.getRequest();
		userManager.adminSave(entity,null);
		return RELOAD;
	}

	/**
	 * 账号禁用
	 */
	@Override
	public String delete() throws Exception {
		HttpServletResponse response= Struts2Utils.getResponse();
		String result="false";
		try{
			userManager.disUser(id);
			result="true";
		}catch (Exception e) {
			// TODO: handle exception
		}
		response.getWriter().write(result);
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		entity=userManager.getModel(id);
	}
	
	public void prepareExecute() throws Exception {
		prepareModel();
	}
	

	public void checkLoginNamelUn() throws Exception{
		HttpServletRequest request= Struts2Utils.getRequest();
		HttpServletResponse response= Struts2Utils.getResponse();
		String loginName=request.getParameter("loginName");
		User user=userManager.findNameUn(id,loginName);
		String result="true";
		if(user!=null){
			result="false";
		}
		response.getWriter().write(result);
	}
	
	public void checkEmailUn() throws Exception{
		HttpServletRequest request= Struts2Utils.getRequest();
		HttpServletResponse response= Struts2Utils.getResponse();
		String email=request.getParameter("email");
		User user=userManager.findEmailUn(id,email);
		String result="true";
		if(user!=null){
			result="false";
		}
		response.getWriter().write(result);
	}
	
}
