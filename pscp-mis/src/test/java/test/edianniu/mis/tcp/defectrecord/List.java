package test.edianniu.mis.tcp.defectrecord;

import com.edianniu.pscp.mis.bean.request.defectrecord.ListRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-09-12 14:20
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002155;

    @Test
    public void test() throws IOException {

        ListRequest request = new ListRequest();
        request.setUid(1271L);
        request.setToken("45184819");
        request.setOffset(0);
        request.setProjectId(1260L);
        request.setStatus(0);

//        request.setOrderId("GD38941505536068");
//        request.setRoomId(1004L);

        ListResponse resp = this.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
