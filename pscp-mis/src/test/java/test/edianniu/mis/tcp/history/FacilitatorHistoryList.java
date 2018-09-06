package test.edianniu.mis.tcp.history;

import com.edianniu.pscp.mis.bean.request.history.FacilitatorHistoryRequest;
import com.edianniu.pscp.mis.bean.response.history.FacilitatorHistoryResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: FacilitatorHistoryList
 * Author: tandingbo
 * CreateTime: 2017-10-30 11:49
 */
public class FacilitatorHistoryList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(FacilitatorHistoryList.class);
    private int msgcode = 1002176;

    @Test
    public void test() throws IOException {
        FacilitatorHistoryRequest request = new FacilitatorHistoryRequest();
        request.setUid(1241L);
        request.setToken("57388449");

        request.setOffset(0);

        FacilitatorHistoryResponse resp = super.sendRequest(request, msgcode, FacilitatorHistoryResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
