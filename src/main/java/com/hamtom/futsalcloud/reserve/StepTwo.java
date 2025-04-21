package com.hamtom.futsalcloud.reserve;

import com.hamtom.futsalcloud.Common;
import com.hamtom.futsalcloud.reserve.dto.EachStepResult;
import com.hamtom.futsalcloud.reserve.dto.ReservationResponse;
import com.hamtom.futsalcloud.reserve.dto.ReservationValues;
import com.hamtom.futsalcloud.reserve.dto.ReservePrivacy;
import com.hamtom.futsalcloud.reserve.enums.ReserveUrl;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hamtom.futsalcloud.Common.*;

public class StepTwo {
    ReservationValues reservationValues;
    ReservePrivacy reservePrivacy;

    public static final Logger logger = LoggerFactory.getLogger(StepTwo.class);

    public StepTwo(ReservationValues reservationValues, ReservePrivacy reservePrivacy) {
        this.reservationValues = reservationValues;
        this.reservePrivacy = reservePrivacy;
    }

    public EachStepResult executeStep(EachStepResult eachStepResult) throws Exception {
        logger.info("============== Step  Two  Start ==============");

        String cookie = eachStepResult.getCookie();
        logger.debug("cookieForHeader: {}", cookie);

        //Header, Body
        Map<String, String> headers = makeHeader("multipart/form-data", cookie);
        Map<String, List<String>> body = makeForm(eachStepResult);

        ReservationResponse reservationResponse = requestReservationWithMapList(makeUrl(ReserveUrl.STEP2), body, headers);

        if(reservationResponse.isSuccess()){
            eachStepResult.setResult(reservationResponse.getResult());
            eachStepResult.setMsg(reservationResponse.getMsg());
            eachStepResult.setReservationNo(reservationResponse.getErntApplcntNo());
        }

        logger.info("=============== Step  Two  END ===============");
        logger.info("");
        return eachStepResult;
    }

    private Map<String, List<String>> makeForm(EachStepResult eachStepResult) {
        String stadiumNo = eachStepResult.getStadiumNo();
        String reservationNo = eachStepResult.getReservationNo();

        String[] emails = reservePrivacy.reserveEmail().split("@");
        String id = emails[0];
        String domain = emails[1];

        Map<String, List<String>> body = new HashMap<>();
        body.put(STADIUM_NO, Collections.singletonList(stadiumNo));
        body.put(RESERVATION_NO, Collections.singletonList(reservationNo));
        body.put("checkAgress", Collections.singletonList("on"));
        body.put("expectNmpr", Collections.singletonList("12"));
        body.put("usePurps", Collections.singletonList("소규모 축구장 이용"));
        body.put("erntApplcntNm", Collections.singletonList(reservePrivacy.reserveName()));
        body.put("erntApplcntGrpNm", Collections.singletonList(""));
        body.put("erntApplcntTelno", Collections.singletonList(""));
        body.put("erntApplcntMbtlnum", Collections.singletonList(reservePrivacy.reservePhone()));
        body.put("email1", Collections.singletonList(id));
        body.put("email2", Collections.singletonList(domain));
        body.put("erntApplcntEmail", Collections.singletonList(reservePrivacy.reserveEmail()));
        body.put("erntApplcntZip", Collections.singletonList(reservePrivacy.reserveZip()));
        body.put("erntApplcntAdres", Collections.singletonList(reservePrivacy.reserveAddr1()));
        body.put("erntApplcntAdresDetail", Collections.singletonList(reservePrivacy.reserveAddr2()));

        return body;
    }
}
