package com.hamtom.futsalcloud.reserve.dto;

import com.hamtom.futsalcloud.reserve.enums.Stadium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationValues {
    private final ReserveDateTimeInfo reserveDateTimeInfo;
    private final StadiumOrder stadiumOrder;

    private final List<String> gameDateTime;
    private final List<String> stadiumList;

    public static final Logger logger = LoggerFactory.getLogger(ReservationValues.class);

    public ReservationValues(ReserveDateTimeInfo reserveDateTimeInfo, StadiumOrder stadiumOrder) {
        this.reserveDateTimeInfo = reserveDateTimeInfo;
        this.stadiumOrder = stadiumOrder;
        this.gameDateTime = makeGameDateTime();
        this.stadiumList = makeStadiumList();

        logger.info("ReservationValues created: {}", this);
    }

    private List<String> makeGameDateTime(){

        List<String> gameDateTimeList = new ArrayList<>();
        String gameDateTime1;

        String setGameDate = reserveDateTimeInfo.manualDate();
        if (reserveDateTimeInfo.isAutoDate()) {
            LocalDateTime plusDays = LocalDateTime.now().plusDays(31);

            DayOfWeek dayOfWeek = plusDays.getDayOfWeek();
            DayOfWeek expectedDayOfWeek = DayOfWeek.of(reserveDateTimeInfo.autoDayOfWeek());
            if (dayOfWeek != expectedDayOfWeek) {
                LocalDateTime previousTuesday = plusDays.with(TemporalAdjusters.previous(expectedDayOfWeek));
                LocalDateTime nextTuesday = plusDays.with(TemporalAdjusters.next(expectedDayOfWeek));

                // 두 화요일 간의 거리 계산
                long daysToPreviousTuesday = plusDays.until(previousTuesday, ChronoUnit.DAYS);
                long daysToNextTuesday = plusDays.until(nextTuesday, ChronoUnit.DAYS);

                // 가장 가까운 화요일 반환
                plusDays = (daysToPreviousTuesday >= daysToNextTuesday) ? previousTuesday : nextTuesday;
            }

            setGameDate = plusDays.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        gameDateTime1 = setGameDate + reserveDateTimeInfo.rentalTime1();
        boolean isValidGameDateTime1 = checkGameDateTime(gameDateTime1);
        if (isValidGameDateTime1) gameDateTimeList.add(gameDateTime1);
        else throw new RuntimeException(String.format("유효하지 않은 포맷입니다. >%s", gameDateTime1));

        if (reserveDateTimeInfo.isTwoHoursRental()) {
            String gameDateTime2 = setGameDate + reserveDateTimeInfo.rentalTime2();
            boolean isValidGameDateTime2 = checkGameDateTime(gameDateTime2);
            if (isValidGameDateTime2) gameDateTimeList.add(gameDateTime2);
            else throw new RuntimeException(String.format("유효하지 않은 포맷입니다. >%s", gameDateTime2));
        }

        return gameDateTimeList;
    }

    private List<String> makeStadiumList(){
        List<String> stadiumList = new ArrayList<>();
        stadiumList.add(Stadium.getStadiumNo(stadiumOrder.stadiumOrder1()));
        stadiumList.add(Stadium.getStadiumNo(stadiumOrder.stadiumOrder2()));
        stadiumList.add(Stadium.getStadiumNo(stadiumOrder.stadiumOrder3()));
        stadiumList.add(Stadium.getStadiumNo(stadiumOrder.stadiumOrder4()));

        for (String stadium : stadiumList) {
            boolean isNumeric = stadium.chars().allMatch(Character::isDigit);
            boolean isValidLength = stadium.length() == 6;
            if (isNumeric && isValidLength) logger.info("{} 유효한 경기장 형식입니다.", stadium);
            else throw new RuntimeException(String.format("유효하지 않은 포맷입니다. >%s", stadium));
        }

        return stadiumList;
    }

    private boolean checkGameDateTime(String gameDateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        sdf.setLenient(false);
        try {
            // 지정된 형식으로 문자열을 파싱하여 날짜/시간 객체 생성
            Date parsedDate = sdf.parse(gameDateTime);

            // 현재 시간 가져오기
            Date currentDate = new Date();

            // 파싱된 날짜/시간이 현재 시간보다 빠른지 검사
            if (parsedDate.before(currentDate)) {
                logger.info("{}는 유효한 형식이지만 현재 시간보다 빠릅니다. 현재 시간: {}", gameDateTime, currentDate);
                return false;
            }

            logger.debug("{}는 유효합니다.", gameDateTime);
            return true;
        } catch (ParseException e) {
            logger.error("{}는 유효하지 않은 형식입니다.", gameDateTime);
            return false;
        }
    }

    public ReserveDateTimeInfo getReserveDateTimeInfo() {
        return reserveDateTimeInfo;
    }

    public List<String> getGameDateTime() {
        return gameDateTime;
    }

    public List<String> getStadiumList() {
        return stadiumList;
    }

    @Override
    public String toString() {
        return "ReservationValues{" +
                "gameDateTime=" + gameDateTime +
                ", stadiumList=" + stadiumList +
                '}';
    }
}
