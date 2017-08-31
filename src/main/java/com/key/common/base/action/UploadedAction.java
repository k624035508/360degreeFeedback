package com.key.common.base.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jielao on 2017/8/12.
 */
@Namespaces(@Namespace("/up"))
@AllowedMethods({"upload1", "upload2"})
public class UploadedAction extends ActionSupport {
    //获取上传文件,名称必须和表单file控件名相同
    private File uploadFile;
    //获取上传文件名,命名格式：表单file控件名+FileName(固定)
    private String uploadfileFileName;
    //获取上传文件类型,命名格式：表单file控件名+ContentType(固定)
    private String uploadfileContentType;

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getUploadfileFileName() {
        return uploadfileFileName;
    }

    public void setUploadfileFileName(String uploadfileFileName) {
        this.uploadfileFileName = uploadfileFileName;
    }

    public String getUploadfileContentType() {
        return uploadfileContentType;
    }

    public void setUploadfileContentType(String uploadfileContentType) {
        this.uploadfileContentType = uploadfileContentType;
    }

    //方法一：使用FileUtils的copyFile来实现文件上传
    public String upload1() throws IOException
    {
        //设置上传文件目录
        String realpath = ServletActionContext.getServletContext().getRealPath("/image");

        //判断上传文件是否为空
        if(uploadFile!=null)
        {
            //设置目标文件（根据 parent 路径名字符串和 child 路径名字符串创建一个新 File 实例）
            File savefile = new File(realpath,uploadfileFileName);

            // 判断上传目录是否存在
            if(!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();

            //把文件uploadfile 拷贝到 savefile 里,FileUtils类需要commons-io-x.x.x.jar包支持
            FileUtils.copyFile(uploadFile,savefile);

            //设置request对象值
            ActionContext.getContext().put("message", "上传成功！");
        }
        return "success";
    }

    //方法二：使用文件流来实现文件上传
    public String upload2() throws IOException
    {
        FileOutputStream fos = new FileOutputStream("C:\\");

        FileInputStream fis = new FileInputStream(uploadFile);

        byte[] buffer = new byte[1024];

        int len = 0;

        while((len=fis.read(buffer))>0)
        {
            fos.write(buffer,0,len);
        }
        return "success";
    }
}
