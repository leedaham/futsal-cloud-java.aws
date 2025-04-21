package com.hamtom.futsalcloud.reserve;

import com.hamtom.futsalcloud.Common;
import com.hamtom.futsalcloud.reserve.dto.EachStepResult;
import com.hamtom.futsalcloud.reserve.dto.ReservationResponse;
import com.hamtom.futsalcloud.reserve.dto.ReservationValues;
import com.hamtom.futsalcloud.reserve.enums.ReserveUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.hamtom.futsalcloud.Common.*;

public class StepOne {
    ReservationValues reservationValues;

    public static final Logger logger = LoggerFactory.getLogger(StepOne.class);
    
    public StepOne(ReservationValues reservationValues) {
        this.reservationValues = reservationValues;
    }

    public EachStepResult executeStep(String cookie) throws Exception {
        logger.info("============== Step  One  Start ==============");

        EachStepResult eachStepResult = new EachStepResult();
        eachStepResult.setCookie(cookie);
        logger.debug("cookieForHeader: {}", cookie);

        //Header, Body
        Map<String, String> headers = makeHeader("multipart/form-data", cookie);
        List<Map<String, List<String>>> bodyList = makeForm();

        ReservationResponse reservationResponse = new ReservationResponse();
        String stadium = "";
        for (Map<String, List<String>> body : bodyList) {
            stadium = body.get(Common.STADIUM_NO).get(0);
            reservationResponse = requestReservationWithMapList(makeUrl(ReserveUrl.STEP1), body, headers);

            if (reservationResponse.isSuccess()) {
                break;
            }
        }

        if(reservationResponse.isSuccess()){
            eachStepResult.setResult(reservationResponse.getResult());
            eachStepResult.setMsg(reservationResponse.getMsg());
            eachStepResult.setStadiumNo(stadium);
            eachStepResult.setReservationNo(reservationResponse.getErntApplcntNo());
        }

        logger.info("=============== Step  One  END ===============");
        logger.info("");
        return eachStepResult;
    }

    private List<Map<String, List<String>>> makeForm() {
        List<Map<String, List<String>>> formList = new ArrayList<>();

        Map<String, List<String>> tempBody = new HashMap<>();
        List<String> gameDateTime = reservationValues.getGameDateTime();
        List<String> stadiumList = reservationValues.getStadiumList();

        tempBody.put("erntYmdh", gameDateTime);
        tempBody.put("dscntChk", Collections.singletonList("N"));
        tempBody.put("dscntPt", Collections.singletonList("0"));
        tempBody.put("dscntAt", Collections.singletonList("N"));
        tempBody.put("dscntNm", Collections.singletonList("해당없음"));
        tempBody.put("noName", Collections.singletonList(""));
        for (String stadium : stadiumList) {
            Map<String, List<String>> bodyBase = new HashMap<>(tempBody);
            bodyBase.put(Common.STADIUM_NO, Collections.singletonList(stadium));
            formList.add(bodyBase);
        }

        return formList;
    }
}
