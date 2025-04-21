package com.hamtom.futsalcloud.reserve.dto;

public class LoginInfo {
    private String userId;
    private String userPwd;

    public LoginInfo(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "userId='" + userId + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }
}
