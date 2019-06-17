package com.arvato.cc.log;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.util.CommonHelper;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 13-6-3
 * Time: 下午2:14
 * To change this template use File | Settings | File Templates.
 */
public class MessageLogger implements Serializable {
    private String appName;
    private Class clz;
    private String method;
    private Date timestamp;
    private String remark;
    private Exception exception;

    public MessageLogger(String appName, Class clz, String method, Timestamp timestamp, String remark) {
        this.appName = appName;
        this.clz = clz;
        this.method = method;
        this.timestamp = timestamp;
        this.remark = remark;
    }

    public MessageLogger(String appName, String method, Date timestamp, String remark, Exception exception) {
        this.appName = appName;
        this.method = method;
        this.timestamp = timestamp;
        this.remark = remark;
        this.exception = exception;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        if (null != this.getException()) {
            StackTraceElement[] stackTraceElements = this.getException().getStackTrace();
            if (null != stackTraceElements) {
                StringBuffer sb = new StringBuffer();
                int i = 0;
                for (StackTraceElement element : stackTraceElements) {
                    if (i == 0) {
                        sb.append("\t\t\t\t\t\t\r\nClass Name:" + element.getClassName());
                        sb.append("\r\n");
                        sb.append("\t\t\t\t\t\tFile Name:" + element.getFileName());
                        sb.append("\r\n");
                        sb.append("\t\t\t\t\t\tMethod Name:" + element.getMethodName());
                        sb.append("\r\n");
                        sb.append("\t\t\t\t\t\tLine Number:" + element.getLineNumber());
                    }
                    i++;
                }
                return String.format(Constants.LOG_TEMPLATE_DETAIL, this.getAppName(), this.getMethod(), CommonHelper.ObjectToTime(this.getTimestamp(),"yyyy-MM-dd hh:mm:ss"), this.getRemark(), sb.toString());
            }
        }

        return String.format(Constants.LOG_TEMPLATE, this.getAppName(), this.getMethod(), CommonHelper.ObjectToTime(this.getTimestamp(),"yyyy-MM-dd hh:mm:ss"), this.getRemark());
    }
}
