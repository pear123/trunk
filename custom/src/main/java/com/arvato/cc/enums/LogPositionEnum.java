package com.arvato.cc.enums;

/**
 * Created with IntelliJ IDEA.
 * User: XURYA01
 * Date: 13-10-23
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
public enum LogPositionEnum {

    /**
     * The START.
     */START("start"),
    /**
     * The PROCESS.
     */PROCESS("process"),
    /**
     * The ERROR.
     */ERROR("error"),
    /**
     * The END.
     */END("end");

    private String value;

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    private LogPositionEnum(String value) {
        this.value = value;
    }
}
