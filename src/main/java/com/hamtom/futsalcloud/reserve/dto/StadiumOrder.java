package com.hamtom.futsalcloud.reserve.dto;

public record StadiumOrder(
        String stadiumOrder1,
        String stadiumOrder2,
        String stadiumOrder3,
        String stadiumOrder4
) {

    @Override
    public String toString() {
        return "StadiumOrder{" +
                "stadiumOrder1='" + stadiumOrder1 + '\'' +
                ", stadiumOrder2='" + stadiumOrder2 + '\'' +
                ", stadiumOrder3='" + stadiumOrder3 + '\'' +
                ", stadiumOrder4='" + stadiumOrder4 + '\'' +
                '}';
    }
}
