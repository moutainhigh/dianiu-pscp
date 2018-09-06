package test.edianniu.cs.tcp.room;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.room.ListRequest;
import com.edianniu.pscp.cs.bean.response.room.ListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class RoomList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(RoomList.class);
    private int msgcode = 1002120;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1226L);
        request.setToken("79449236");


        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
