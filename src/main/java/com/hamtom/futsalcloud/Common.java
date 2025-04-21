package com.hamtom.futsalcloud;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import com.hamtom.futsalcloud.reserve.dto.ReservationResponse;
import com.hamtom.futsalcloud.reserve.enums.ReserveUrl;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class Common {
    public static final String STADIUM_NO = "erntResveNo";
    public static final String RESERVATION_NO = "erntApplcntNo";
    public static final Logger logger = LoggerFactory.getLogger(Common.class);

    public static String extractCookieValue(List<String> cookies, String cookieName) {
        if (cookies == null) {
            return null;
        }
        for (String cookie : cookies) {
            if (cookie.startsWith(cookieName + "=")) {
                return cookie.split(";")[0].split("=")[1];
            }
        }
        return null;
    }

    public static String makeUrl(ReserveUrl url) {
        return String.format("https://%s%s", ReserveUrl.HOST.getValue(), url.getValue());
    }

    public static String mapToFormData(Map<String, String> data) {
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            joiner.add(entry.getKey() + "=" + entry.getValue());
        }
        return joiner.toString();
    }

    public static String mapToListFormData(Map<String, List<String>> data) {
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            for(String key : entry.getValue()) {
                joiner.add(key + "=" + entry.getValue());
            }
        }
        return joiner.toString();
    }

    public static Map<String, String> makeHeader(String mediaType) {
        return Map.of(
                "Content-Type", mediaType,
                "Accept-Encoding", "gzip, deflate, br",
                "Accept", "*/*"
        );
    }

    public static Map<String, String> makeHeader(String mediaType, String cookie) {
        return Map.of(
                "Content-Type", mediaType,
                "Accept-Encoding", "gzip, deflate, br",
                "Accept", "*/*",
                "Cookie", cookie
        );
    }

    public static ReservationResponse requestReservation(String url, Map<String, String> body, Map<String, String> headers) throws Exception {
        logger.info("Request URL: {}", url);
        logger.info("Request Headers: {}", headers);
        logger.info("Request Body: {}", body);

        String formData = mapToFormData(body);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(formData, StandardCharsets.UTF_8));

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        logger.info("Response Code: {}", response.statusCode());
        String responseBody = response.body();
        logger.info("Response Body: {}", responseBody);

        if (Objects.isNull(responseBody)) {
            throw new RuntimeException("No Response!");
        }

        JsonElement json = JsonParser.parseString(responseBody);
        return new Gson().fromJson(json, ReservationResponse.class);
    }

    public static ReservationResponse requestReservationWithMapList(String url, Map<String, List<String>> body, Map<String, String> headers) throws Exception {
        logger.info("Request URL: {}", url);
        logger.info("Request Headers: {}", headers);
        logger.info("Request Body: {}", body);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        for (Map.Entry<String, List<String>> entry : body.entrySet()) {
            String key = entry.getKey();
            for (String value : entry.getValue()) {
                builder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset(StandardCharsets.UTF_8));
            }
        }

        HttpEntity multipart = builder.build();

        HttpPost post = new HttpPost(url);
        post.setEntity(multipart);

        // 헤더 설정 (Content-Type은 multipart 자동으로 세팅되니까 건들지 마라!)
        for (Map.Entry<String, String> header : headers.entrySet()) {
            if (!header.getKey().equalsIgnoreCase("Content-Type")) {
                post.setHeader(header.getKey(), header.getValue());
            }
        }

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Response Code: {}", statusCode);

            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            logger.info("Response Body: {}", responseBody);

            if (Objects.isNull(responseBody)) {
                throw new RuntimeException("No Response!");
            }

            JsonElement json = JsonParser.parseString(responseBody);
            return new Gson().fromJson(json, ReservationResponse.class);
        }
    }

//    public static ReservationResponse requestReservationWithMapList(String url, Map<String, List<String>> body, Map<String, String> headers) throws Exception {
//        logger.info("Request URL: {}", url);
//        logger.info("Request Headers: {}", headers);
//        logger.info("Request Body: {}", body);
//
//        String formData = mapToListFormData(body);
//
//        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .POST(HttpRequest.BodyPublishers.ofString(formData, StandardCharsets.UTF_8));
//
//        for (Map.Entry<String, String> entry : headers.entrySet()) {
//            requestBuilder.header(entry.getKey(), entry.getValue());
//        }
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = requestBuilder.build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
//
//        logger.info("Response Code: {}", response.statusCode());
//        String responseBody = response.body();
//        logger.info("Response Body: {}", responseBody);
//
//        if (Objects.isNull(responseBody)) {
//            throw new RuntimeException("No Response!");
//        }
//
//        JsonElement json = JsonParser.parseString(responseBody);
//        return new Gson().fromJson(json, ReservationResponse.class);
//    }

    public static HttpResponse<String> requestLogin(String url, Map<String, String> body, Map<String, String> headers) throws Exception {
        logger.info("Request URL: {}", url);
        logger.info("Request Headers: {}", headers);
        logger.info("Request Body: {}", body);

        String formData = mapToFormData(body);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(formData, StandardCharsets.UTF_8));

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        logger.info("Response Code: {}", response.statusCode());
        String responseBody = response.body();
        logger.info("Response Body: {}", responseBody);

        if (Objects.isNull(responseBody)) {
            throw new RuntimeException("No Response!");
        }

        return response;
    }
}