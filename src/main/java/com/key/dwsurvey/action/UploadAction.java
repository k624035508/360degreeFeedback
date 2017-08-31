package com.key.dwsurvey.action;

import java.io.File;

import com.key.common.utils.FileUtils;
import org.apache.struts2.ServletActionContext;


/**
 * Created by jielao on 2017/8/21.
 */
public class UploadAction {
    //接受拦截器传入的临时文件
    private File some;
    //接受拦截器注入的原始文件名
    private String someFileName;

    public String execute(){
        if(some==null)
            return "error";
        //将文件放于项目部署路径下的upload文件夹下
        String path="WEB-INF/upload/"+ someFileName;
        //根据相对部署路径计算完整路径
        path=ServletActionContext.getServletContext().getRealPath(path);
        //将临时文件复制到上述路径下
        FileUtils.copy(some, new File(path));
        return "success";
    }

    public File getSome() {
        return some;
    }

    public void setSome(File some) {
        this.some = some;
    }

    public String getSomeFileName() {
        return someFileName;
    }

    public void setSomeFileName(String someFileName) {
        this.someFileName = someFileName;
    }
}
