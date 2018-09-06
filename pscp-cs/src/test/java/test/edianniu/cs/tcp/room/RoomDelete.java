package test.edianniu.cs.tcp.room;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.room.SaveRequest;
import com.edianniu.pscp.cs.bean.response.room.SaveResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class RoomDelete extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(RoomDelete.class);
    private int msgcode = 1002121;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1237L);
        request.setToken("79449236");
        
        request.setId(1021L);
        
        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
