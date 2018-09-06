package test.edianniu.cs.tcp.company;

import com.edianniu.pscp.cs.bean.request.company.BindingFacilitatorRequest;
import com.edianniu.pscp.cs.bean.response.company.BindingFacilitatorResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: BindingFacilitatorList
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:31
 */
public class BindingFacilitatorList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(BindingFacilitatorList.class);
    private int msgcode = 1002177;

    @Test
    public void test() throws IOException {
        BindingFacilitatorRequest request = new BindingFacilitatorRequest();
        request.setUid(1184L);
        request.setToken("79449236");

        BindingFacilitatorResponse resp = super.sendRequest(request, msgcode, BindingFacilitatorResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
