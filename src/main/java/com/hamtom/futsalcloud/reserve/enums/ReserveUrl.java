package com.hamtom.futsalcloud.reserve.enums;

public enum ReserveUrl {
    HOST("host", "www.geumcheon.go.kr"),
    LOGIN("login-url", "/portal/login.do"),
    STEP1("step1-url", "/reserve/saveErntApplcntStep01.do"),
    STEP2("step2-url", "/reserve/saveErntApplcntStep02.do"),
    STEP3("step3-url", "/reserve/saveErntApplcntStep03.do");

    private final String name;
    private final String value;

    ReserveUrl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
