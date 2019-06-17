package com.arvato.cc.log;


import com.arvato.cc.enums.LogPositionEnum;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: XURYA01
 * Date: 13-10-23
 * Time: 下午5:31
 * To change this template use File | Settings | File Templates.
 */
public class LogMessageUtil {

    private String logKey;

    private String uuidKey;

    private String monitoringKey;

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public LogMessageUtil(String logKey) {
        this.logKey = logKey;
//        setUuidKey(UUID.randomUUID().toString());
    }

    public String getStartMessage(String message) {
        StringBuffer msg = new StringBuffer();
        msg.append(message);
        msg.append("|");
        msg.append(LogPositionEnum.START.name());
//        msg.append("|");
//        msg.append(getUuidKey());
        msg.append("|");
        msg.append(logKey);
        if (StringUtils.isNotBlank(monitoringKey)) {
            msg.append("|");
            msg.append(monitoringKey);
        }else{
            msg.append("|");
            msg.append("monitoring");
        }
        return msg.toString();
    }

    public String getEndMessage(String message) {
        StringBuffer msg = new StringBuffer();
        msg.append(message);
        msg.append("|");
        msg.append(LogPositionEnum.END.name());
//        msg.append("|");
//        msg.append(getUuidKey());
        msg.append("|");
        msg.append(logKey);
        if (StringUtils.isNotBlank(monitoringKey)) {
            msg.append("|");
            msg.append(monitoringKey);
        }else{
            msg.append("|");
            msg.append("monitoring");
        }
        return msg.toString();
    }

    public String getErrorMessage(String message) {
        StringBuffer msg = new StringBuffer();
        msg.append(message);
        msg.append("|");
        msg.append(LogPositionEnum.ERROR.name());
//        msg.append("|");
//        msg.append(getUuidKey());
        msg.append("|");
        msg.append(logKey);
        if (StringUtils.isNotBlank(monitoringKey)) {
            msg.append("|");
            msg.append(monitoringKey);
        }else{
            msg.append("|");
            msg.append("monitoring");
        }
        return msg.toString();
    }

    public String getProcessMessage(String message) {
        StringBuffer msg = new StringBuffer();
        msg.append(message);
        msg.append("|");
        msg.append(LogPositionEnum.PROCESS.name());
//        msg.append("|");
//        msg.append(getUuidKey());
        msg.append("|");
        msg.append(logKey);
        if (StringUtils.isNotBlank(monitoringKey)) {
            msg.append("|");
            msg.append(monitoringKey);
        }else{
            msg.append("|");
            msg.append("monitoring");
        }
        return msg.toString();
    }
}
