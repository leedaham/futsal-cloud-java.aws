package com.hamtom.futsalcloud.reserve;

import com.hamtom.futsalcloud.Common;
import com.hamtom.futsalcloud.reserve.dto.LoginCookie;
import com.hamtom.futsalcloud.reserve.dto.LoginInfo;
import com.hamtom.futsalcloud.reserve.dto.ReservationResponse;
import com.hamtom.futsalcloud.reserve.enums.ReserveUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.*;

import static com.hamtom.futsalcloud.Common.*;
import static com.hamtom.futsalcloud.reserve.dto.LoginCookie.JSESSIONID_KEY;
import static com.hamtom.futsalcloud.reserve.dto.LoginCookie.WMONID_KEY;

public class Login {
    private final String userId;
    private final String userPasswd;
    public static final Logger logger = LoggerFactory.getLogger(Login.class);

    public Login(LoginInfo loginInfo) {
        this.userId = loginInfo.getUserId();
        this.userPasswd = loginInfo.getUserPwd();
    }

    public LoginCookie executeLogin() throws Exception {
        logger.info("================ Login Start ================");

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("userId", userId);
        loginParams.put("userPasswd", userPasswd);

        //Header, Body
        Map<String, String> headers = makeHeader("application/x-www-form-urlencoded");
        HttpResponse<String> response = requestLogin(makeUrl(ReserveUrl.LOGIN), loginParams, headers);

        // 응답 확인
        LoginCookie loginCookie = checkResponse(response);

        logger.info("================  Login End  ================");
        logger.info("");
        return loginCookie;
    }

    public LoginCookie checkResponse(HttpResponse<String> response) {
        int responseCode = response.statusCode();
        String responseBody = Optional.ofNullable(response.body()).orElse("");

        logger.info("Response Code: {}", responseCode);
        logger.info("Response Body: {}", responseBody);

        boolean loginStatus = responseBody.contains("이미 로그인이 되어 있습니다.") || responseBody.contains("The document has been moved.");

        if (!loginStatus) throw new RuntimeException("Login 실패");

        HttpHeaders responseHeaders = response.headers();
        List<String> cookies = responseHeaders.allValues("set-cookie");

        String wmonid = Optional.ofNullable(extractCookieValue(cookies, WMONID_KEY)).orElse("");
        String jsessionid = Optional.ofNullable(extractCookieValue(cookies, JSESSIONID_KEY)).orElse("");
        logger.info(WMONID_KEY + ": {}", wmonid);
        logger.info(JSESSIONID_KEY + ": {}", jsessionid);

        if (wmonid.isEmpty() || jsessionid.isEmpty()) {
            throw new RuntimeException("No Cookies!");
        }

        return LoginCookie.makeSuccessCookie(true, jsessionid, wmonid);
    }
}
