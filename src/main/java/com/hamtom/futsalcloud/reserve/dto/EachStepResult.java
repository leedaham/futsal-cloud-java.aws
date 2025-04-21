package com.hamtom.futsalcloud.reserve.dto;

import com.hamtom.futsalcloud.reserve.enums.Stadium;

public class EachStepResult {
    private String cookie;
    private String result;
    private String msg;
    private String stadiumNo;
    private String reservationNo;

    public boolean isResultExist() {
        if (result == null) {
            return false;
        }

        return result.equalsIgnoreCase("S") || result.equalsIgnoreCase("F");
    }

    public boolean isSuccess() {
        if (result == null) {
            return false;
        }

        return result.equalsIgnoreCase("S");
    }

    public boolean isFailure() {
        return !isSuccess();
    }

//    public String finishReport(){
//        String stadiumName = Stadium.getStadiumNo(this.stadiumNo);
//        return (isSuccess() && stadiumName !=null) ? stadiumName + " 구장 예약 성공" : "구장 예약 실패";
//    }

    public EachStepResult() {
    }

    public EachStepResult(String cookie, String result, String msg, String stadiumNo, String reservationNo) {
        this.cookie = cookie;
        this.result = result;
        this.msg = msg;
        this.stadiumNo = stadiumNo;
        this.reservationNo = reservationNo;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStadiumNo() {
        return stadiumNo;
    }

    public void setStadiumNo(String stadiumNo) {
        this.stadiumNo = stadiumNo;
    }

    public String getReservationNo() {
        return reservationNo;
    }

    public void setReservationNo(String reservationNo) {
        this.reservationNo = reservationNo;
    }

    @Override
    public String toString() {
        return "EachStepResult{" +
                "cookie='" + cookie + '\'' +
                ", result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", stadiumNo='" + stadiumNo + '\'' +
                ", reservationNo='" + reservationNo + '\'' +
                '}';
    }
}

