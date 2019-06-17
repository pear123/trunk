package com.arvato.platform.jobs.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-14
 * Time: 上午9:23
 * To change this template use File | Settings | File Templates.
 */

public class ResponseBean implements Serializable {
    private String resultFlag;
    private String resultCode;
    private String resutlDesc;

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResutlDesc() {
        return resutlDesc;
    }

    public void setResutlDesc(String resutlDesc) {
        this.resutlDesc = resutlDesc;
    }
}
