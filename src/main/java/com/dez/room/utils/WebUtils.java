package com.dez.room.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebUtils {

    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    private String getClientIp() {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

}
