package com.hamtom.futsalcloud.reserve;

import com.hamtom.futsalcloud.reserve.dto.EachStepResult;
import com.hamtom.futsalcloud.reserve.dto.ReservationResponse;
import com.hamtom.futsalcloud.reserve.enums.ReserveUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hamtom.futsalcloud.Common.*;

public class StepThree {

    public static final Logger logger = LoggerFactory.getLogger(StepThree.class);

    public EachStepResult executeStep(EachStepResult eachStepResult) throws Exception {
        logger.info("============== Step Three Start ==============");

        String cookie = eachStepResult.getCookie();
        logger.debug("cookieForHeader: {}", cookie);

        //Header, Body
        Map<String, String> headers = makeHeader("multipart/form-data", cookie);
        Map<String, List<String>> body = makeForm(eachStepResult);

        //request > response
        ReservationResponse reservationResponse = requestReservationWithMapList(makeUrl(ReserveUrl.STEP3), body, headers);

        if (reservationResponse.isSuccess()) {
            eachStepResult.setResult(reservationResponse.getResult());
            eachStepResult.setMsg(reservationResponse.getMsg());
        }

        logger.info("=============== Step Three END ===============");
        logger.info("");
        return eachStepResult;
    }

    private Map<String, List<String>> makeForm(EachStepResult eachStepResult) {
        String stadiumNo = eachStepResult.getStadiumNo();
        String reservationNo = eachStepResult.getReservationNo();

        Map<String, List<String>> body = new HashMap<>();
        body.put(STADIUM_NO, Collections.singletonList(stadiumNo));
        body.put(RESERVATION_NO, Collections.singletonList(reservationNo));
        body.put("noneName", Collections.singletonList(""));

        return body;
    }
}
