package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.ListRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: ListWorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-26 10:38
 */
public class ListWorkOrder extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ListWorkOrder.class);
    private int msgcode = 1002017;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1297L);
        request.setToken("77433664");

        request.setOffset(0);
        request.setLatitude("30.183251");
        request.setLongitude("120.149388");
        request.setStatus("finished");
        request.setType("chief");
        request.setCompanyId(1085L);

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
