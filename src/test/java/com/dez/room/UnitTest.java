package com.dez.room;

import com.dez.room.util.IpUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class UnitTest {

    @Test
    public void getIpTest(){
        MockHttpServletRequest request =  new MockHttpServletRequest();

        request.setRemoteAddr("127.0.0.1");
        String ip = IpUtil.fetchClientIpAddr(request);
        Assert.assertTrue( "Illegal IP: " + ip,ip.chars().filter($ -> $ == '.').count() == 3);

        request.setRemoteAddr("22.33.44.55");
        ip = IpUtil.fetchClientIpAddr(request);
        Assert.assertTrue( "Illegal IP: " + ip,ip.chars().filter($ -> $ == '.').count() == 3);

        request.setRemoteAddr(null);
        ip = IpUtil.fetchClientIpAddr(request);
        Assert.assertTrue( "Illegal IP: " + ip,ip.chars().filter($ -> $ == '.').count() == 3);
    }
}
