package com.hamtom.futsalcloud.reserve.dto;

public class LoginCookie {
    public static final String JSESSIONID_KEY = "JSESSIONID";
    public static final String WMONID_KEY = "WMONID";

    private boolean loginStatus;
    private String JSESSIONID;
    private String WMONID;

    public String makeCookieForHeader() {
        return String.format("%s=%s; %s=%s", JSESSIONID_KEY, JSESSIONID, WMONID_KEY, WMONID);
    }

    public boolean isSuccess () {
        return loginStatus;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    private LoginCookie() {
    }

    public LoginCookie(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    private LoginCookie(boolean loginStatus, String JSESSIONID, String WMONID) {
        this.loginStatus = loginStatus;
        this.JSESSIONID = JSESSIONID;
        this.WMONID = WMONID;
    }

    public static LoginCookie makeFailureCookie() {
        return new LoginCookie(false);
    }

    public static LoginCookie makeSuccessCookie(boolean loginStatus, String JSESSIONID, String WMONID) {
        return new LoginCookie(loginStatus, JSESSIONID, WMONID);
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public String getWMONID() {
        return WMONID;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public void setWMONID(String WMONID) {
        this.WMONID = WMONID;
    }

    @Override
    public String toString() {
        return "LoginCookie{" +
                "loginStatus=" + loginStatus +
                ", JSESSIONID='" + JSESSIONID + '\'' +
                ", WMONID='" + WMONID + '\'' +
                '}';
    }
}
