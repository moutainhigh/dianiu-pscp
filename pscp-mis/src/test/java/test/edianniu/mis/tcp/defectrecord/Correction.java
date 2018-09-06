package test.edianniu.mis.tcp.defectrecord;

import com.edianniu.pscp.mis.bean.request.defectrecord.CorrectionRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.CorrectionResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Correction
 * Author: tandingbo
 * CreateTime: 2017-09-12 18:13
 */
public class Correction extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Correction.class);
    private int msgcode = 1002158;

    @Test
    public void test() throws IOException {

        CorrectionRequest request = new CorrectionRequest();
        request.setUid(1356L);
        request.setToken("26227159");

        request.setOrderId("GD154931854343558");
        request.setId(1000L);
        request.setSolver("tanbobo2");
        request.setSolveDate("2017-09-11");
        request.setSolveRemark("测试缺陷解决备注1");


        CorrectionResponse resp = this.sendRequest(request, msgcode, CorrectionResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}