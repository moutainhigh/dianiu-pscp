package test.edianniu.mis.tcp.workorder;

import com.edianniu.pscp.mis.bean.request.workorder.RoomListRequest;
import com.edianniu.pscp.mis.bean.response.workorder.RoomListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: RoomList
 * Author: tandingbo
 * CreateTime: 2017-09-15 17:02
 */
public class RoomList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(RoomList.class);
    private static final int msgcode = 1002174;

    @Test
    public void test() throws IOException {

        RoomListRequest request = new RoomListRequest();
        request.setUid(1185L);
        request.setToken("26227159");

        request.setOrderId("GD38941505536068");

        RoomListResponse resp = this.sendRequest(request, msgcode, RoomListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}