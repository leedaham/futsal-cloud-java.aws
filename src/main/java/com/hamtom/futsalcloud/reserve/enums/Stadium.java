package com.hamtom.futsalcloud.reserve.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Stadium {
    STADIUM_1_1("1-1", "140105"),
    STADIUM_1_2("1-2", "140107"),
    STADIUM_2_1("2-1", "140326"),
    STADIUM_2_2("2-2", "140327");

    private final String stadiumName;
    private final String stadiumNo;

    Stadium(String stadiumName, String stadiumNo) {
        this.stadiumName = stadiumName;
        this.stadiumNo = stadiumNo;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public String getStadiumNo() {
        return stadiumNo;
    }

    private static final Logger logger = LoggerFactory.getLogger(Stadium.class);


    public static String getStadiumNo(String stadiumName){
        for(Stadium stadium : Stadium.values()){
            if(stadium.getStadiumName().equals(stadiumName)){
                return stadium.getStadiumNo();
            }
        }
        logger.info("No stadium found for stadiumName={}", stadiumName);
        return null;
    }
}
