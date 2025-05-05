package com.hamtom.futsalcloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamtom.futsalcloud.reserve.Login;
import com.hamtom.futsalcloud.reserve.StepOne;
import com.hamtom.futsalcloud.reserve.StepThree;
import com.hamtom.futsalcloud.reserve.StepTwo;
import com.hamtom.futsalcloud.reserve.dto.EachStepResult;
import com.hamtom.futsalcloud.reserve.dto.LoginCookie;
import com.hamtom.futsalcloud.reserve.dto.RequestDTO;
import com.hamtom.futsalcloud.reserve.dto.ReservationValues;

import java.time.LocalDateTime;
import java.util.Map;

public class LambdaHandler implements RequestHandler<Map<String, Object>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static LambdaLogger logger;

    @Override
    public String handleRequest(Map<String, Object> event, Context context) {
        setLogger(context);

        logger.log("********* Lambda START "+ LocalDateTime.now());
        try {
            Thread.sleep(45_000); // 49초 대기
            logger.log("awake "+ LocalDateTime.now());
        } catch (InterruptedException e) {
            logger.log(e.getMessage());
        }

        logger.log("********* Process START "+ LocalDateTime.now());
        RequestDTO requestDTO = mapToRequestDTO(event);
        ReservationValues reservationValues = new ReservationValues(requestDTO.getReserveDateTimeInfo(), requestDTO.getStadiumOrder());

        try {
            //Login
            Login login = new Login(requestDTO.getLoginInfo());
            LoginCookie loginCookie = login.executeLogin();
            logger.log("* [RESULT]     Login: " + loginCookie.toString());
            if(loginCookie.isFailure()) return "Fail..";

            String cookie = loginCookie.makeCookieForHeader();

            StepOne stepOne = new StepOne(reservationValues);
            EachStepResult eachStepResult = stepOne.executeStep(cookie);
            logger.log("* [RESULT]   StepOne: " + eachStepResult.toString());
            if(eachStepResult.isFailure()) return "Fail..";

            StepTwo stepTwo = new StepTwo(reservationValues, requestDTO.getReservePrivacy());
            eachStepResult = stepTwo.executeStep(eachStepResult);
            logger.log("* [RESULT]   StepTwo: " + eachStepResult.toString());
            if(eachStepResult.isFailure()) return "Fail..";

            StepThree stepThree = new StepThree();
            eachStepResult = stepThree.executeStep(eachStepResult);
            logger.log("* [RESULT]   StepThree: " + eachStepResult.toString());
            if(eachStepResult.isFailure()) return "Fail..";


        } catch (Exception e) {
            logger.log("********* ERROR ");
            throw new RuntimeException("Error in handling request", e);
        }
        logger.log("********* END ");
        return "Success!";
    }

    public void setLogger(Context context){
        logger = context.getLogger();
    }

    public RequestDTO mapToRequestDTO(Map<String, Object> event) {
        return new RequestDTO(event);
    }


    public void loggingPayload(Map<String, Object> event) {
        try {
            String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
            logger.log("Received payload:\n" + jsonPayload);
        } catch (Exception e) {
            logger.log("Error processing payload: " + e.getMessage());
        }
    }
}
