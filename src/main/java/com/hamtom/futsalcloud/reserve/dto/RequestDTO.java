package com.hamtom.futsalcloud.reserve.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RequestDTO {
    private final LoginInfo loginInfo;
    private final ReserveDateTimeInfo reserveDateTimeInfo;
    private final StadiumOrder stadiumOrder;
    private final ReservePrivacy reservePrivacy;

    public RequestDTO(Map<String, Object> payload) {
        Logger logger = LoggerFactory.getLogger(RequestDTO.class);
        try {
            this.loginInfo = new LoginInfo(
                    (String) payload.get("user-id"),
                    (String) payload.get("user-pwd")
            );
            this.reserveDateTimeInfo = new ReserveDateTimeInfo(
                    (Boolean) payload.get("is-auto-date"),
                    (Integer) payload.get("auto-day-of-week"),
                    (String) payload.get("manual-date"),
                    (Boolean) payload.get("is-two-hours-rental"),
                    (String) payload.get("rental-time1"),
                    (String) payload.get("rental-time2")
            );
            this.stadiumOrder = new StadiumOrder(
                    (String) payload.get("stadium-order1"),
                    (String) payload.get("stadium-order2"),
                    (String) payload.get("stadium-order3"),
                    (String) payload.get("stadium-order4")
            );
            this.reservePrivacy = new ReservePrivacy(
                    (String) payload.get("reserve-name"),
                    (String) payload.get("reserve-phone"),
                    (String) payload.get("reserve-email"),
                    (String) payload.get("reserve-addr1"),
                    (String) payload.get("reserve-addr2"),
                    (String) payload.get("reserve-zip")
            );
        }catch (Exception e){
            logger.error("Error in RequestDTO", e);
            throw new RuntimeException();
        }
        logger.info("RequestDTO created: {}", this);
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public ReserveDateTimeInfo getReserveDateTimeInfo() {
        return reserveDateTimeInfo;
    }

    public StadiumOrder getStadiumOrder() {
        return stadiumOrder;
    }

    public ReservePrivacy getReservePrivacy() {
        return reservePrivacy;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "loginInfo=" + loginInfo.toString() +
                ", reserveDateTimeInfo=" + reserveDateTimeInfo.toString() +
                ", stadiumOrder=" + stadiumOrder.toString() +
                ", reservePrivacy=" + reservePrivacy.toString() +
                '}';
    }
}



