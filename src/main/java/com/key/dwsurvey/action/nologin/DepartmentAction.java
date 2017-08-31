package com.key.dwsurvey.action.nologin;

import com.key.common.base.entity.User;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.service.UserManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jielao on 2017/8/7.
 */
@Namespace("/")
@InterceptorRefs({@InterceptorRef("paramsPrepareParamsStack")})
@Results({
        @Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/page/content/feedback-review/userSelector.jsp", type = Struts2Utils.DISPATCHER)
})
public class DepartmentAction extends ActionSupport {

    @Autowired
    private UserManager userManager;

    private String departmentId;

    public String execute() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        List<User> userList = userManager.findUserListByDepartment(departmentId);
        request.setAttribute("userList", userList);
        String dimensionId = request.getParameter("dimensionId");
        request.setAttribute("dimensionId", dimensionId);
        return SUCCESS;
    }

    public String getDepartmentId(){ return departmentId; }

    public void setDepartmentId(String departmentId){ this.departmentId = departmentId; }
}
