package com.key.common.utils.excel;

/**
 * 类主要功能：Excel模板上传Excel数据返回结果对象
 * Created by jielao on 2017/8/11.
 */
public class ExceluptempRetVo {
    private Integer successflag; //1:成功, 0:不成功
    private String retError; //为0时需对不成功的信息进行赋值
    private String[] retParaArr; //数组对象，用作回传值使用
    private String retjson; //成功时返回的json数据

    public Integer getSuccessflag() {
        return successflag;
    }

    public void setSuccessflag(Integer successflag) {
        this.successflag = successflag;
    }

    public String getRetError() {
        return retError;
    }

    public void setRetError(String retError) {
        this.retError = retError;
    }

    public String[] getRetParaArr() {
        return retParaArr;
    }

    public void setRetParaArr(String[] retParaArr) {
        this.retParaArr = retParaArr;
    }

    public String getRetjson() {
        return retjson;
    }

    public void setRetjson(String retjson) {
        this.retjson = retjson;
    }
}
