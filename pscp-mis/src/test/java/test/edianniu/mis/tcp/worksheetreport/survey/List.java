package test.edianniu.mis.tcp.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.request.worksheetreport.survey.ListRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.survey.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * @author tandingbo
 * @create 2017-11-08 14:54
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002178;

    @Test
    public void test() throws IOException {

        ListRequest request = new ListRequest();
        request.setUid(1356L);
        request.setToken("26227159");
        request.setOffset(0);

        request.setOrderId("GD154931854343558");

        ListResponse resp = this.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
