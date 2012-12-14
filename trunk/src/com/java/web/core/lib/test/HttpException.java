package com.java.web.core.lib.test;

import java.io.IOException;

public class HttpException extends IOException {

    private int code = 0;

    public HttpException(int code, String message) {
        super(String.format("{0} - {1}", code, message));
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
