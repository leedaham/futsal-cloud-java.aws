package com.hamtom.futsalcloud.reserve.dto;

public record ReservePrivacy(
        String reserveName,
        String reservePhone,
        String reserveEmail,
        String reserveAddr1,
        String reserveAddr2,
        String reserveZip
) {

    @Override
    public String toString() {
        return "ReservePrivacy{" +
                "reserveName='" + reserveName + '\'' +
                ", reservePhone='" + reservePhone + '\'' +
                ", reserveEmail='" + reserveEmail + '\'' +
                ", reserveAddr1='" + reserveAddr1 + '\'' +
                ", reserveAddr2='" + reserveAddr2 + '\'' +
                ", reserveZip='" + reserveZip + '\'' +
                '}';
    }
}
