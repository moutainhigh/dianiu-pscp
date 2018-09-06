package test.edianniu.mis.tcp.worksheetreport.patrol;

import com.edianniu.pscp.mis.bean.request.worksheetreport.patrol.ListRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.patrol.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-09-13 11:52
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002164;

    @Test
    public void test() throws IOException {

        ListRequest request = new ListRequest();
        request.setUid(1185L);
        request.setToken("26227159");
        request.setOffset(0);

        request.setOrderId("GD38941505536068");

        ListResponse resp = this.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}