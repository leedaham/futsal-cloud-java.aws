package com.hamtom.futsalcloud.reserve.dto;

public class ReservationResponse {
    private String result;
    private String msg;
    private String erntApplcntNo;


    public ReservationResponse() {
    }

    public ReservationResponse(String result, String msg, String erntApplcntNo) {
        this.result = result;
        this.msg = msg;
        this.erntApplcntNo = erntApplcntNo;
    }

    public boolean isEmpty() {
        return result == null;
    }
    public boolean isSuccess() {
        return result.equalsIgnoreCase("S");
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getErntApplcntNo() {
        return erntApplcntNo;
    }

    @Override
    public String toString() {
        return "ReservationResponse{" +
                "result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", erntApplcntNo='" + erntApplcntNo + '\'' +
                '}';
    }
}
