package com.dez.room.util;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class IpUtil {
    @SuppressWarnings("ConstantConditions")
    public static String fetchClientIpAddr(HttpServletRequest request) {
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1") || ip == null){
            ip = "127.0.0.1";
        }
        return ip;
    }
}
