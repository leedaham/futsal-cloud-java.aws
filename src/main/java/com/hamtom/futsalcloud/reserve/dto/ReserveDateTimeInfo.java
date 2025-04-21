package com.hamtom.futsalcloud.reserve.dto;

public record ReserveDateTimeInfo(
        boolean isAutoDate,
        int autoDayOfWeek,
        String manualDate,
        boolean isTwoHoursRental,
        String rentalTime1,
        String rentalTime2
) {

    @Override
    public String toString() {
        return "ReserveDateTimeInfo{" +
                "isAutoDate=" + isAutoDate +
                ", autoDayOfWeek='" + autoDayOfWeek + '\'' +
                ", manualDate='" + manualDate + '\'' +
                ", isTwoHoursRental=" + isTwoHoursRental +
                ", rentalTime1='" + rentalTime1 + '\'' +
                ", rentalTime2='" + rentalTime2 + '\'' +
                '}';
    }
}
