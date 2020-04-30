package com.dez.room.Controller;

import com.dez.room.Repo.RoomRepo;
import com.dez.room.data.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class IndexController {

    private RoomRepo roomRepo;

    public IndexController(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping()
    public String getIndexPage(Model model, HttpServletRequest request){

        ArrayList<String> arrayCountries = new ArrayList<>();
        arrayCountries.add("Belarus");
        arrayCountries.add("Russian");
        arrayCountries.add("Germany");

        model.addAttribute("countries", arrayCountries);
        model.addAttribute("rooms", roomRepo.findAll());

        ArrayList<String> arrays = new ArrayList<>();
        arrays.add(request.getRemoteAddr());
        arrays.add(request.getRemoteHost());
        arrays.add(request.getRemoteUser());
        Integer a = request.getRemotePort(); arrays.add(a.toString());

        arrays.add(getClientIp(request));
        arrays.add(getClientIpAddress(request));
        arrays.add(fetchClientIpAddr());

        model.addAttribute("arrays",arrays);
        model.addAttribute("maps",getRequestHeadersInMap(request));

        return "index";
    }

    @PostMapping()
    public String addCountry(Room room){

        room.setLamp(false);
        roomRepo.save(room);
        return "redirect:/";
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

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    private Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {

        Map<String, String> result = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }

        return result;
    }

    @SuppressWarnings("ConstantConditions")
    protected String fetchClientIpAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
        Assert.isTrue(ip.chars().filter($ -> $ == '.').count() == 3, "Illegal IP: " + ip);
        return ip;
    }

}
