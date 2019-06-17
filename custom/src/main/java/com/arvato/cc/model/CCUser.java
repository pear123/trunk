package com.arvato.cc.model;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-24
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public class CCUser {

    private String userSysId;
    private String userId;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userRemark;
    private String userStatus;
    private String userRoles;

    public String getUserSysId() {
        return userSysId;
    }

    public void setUserSysId(String userSysId) {
        this.userSysId = userSysId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }
}
