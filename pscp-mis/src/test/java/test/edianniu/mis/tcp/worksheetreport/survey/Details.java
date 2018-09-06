package test.edianniu.mis.tcp.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.request.worksheetreport.survey.DetailsRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.survey.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * 勘察报告详情接口测试
 *
 * @author tandingbo
 * @create 2017-11-08 17:09
 */
public class Details extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Details.class);
    private int msgcode = 1002179;

    @Test
    public void test() throws IOException {

        DetailsRequest request = new DetailsRequest();
        request.setUid(1356L);
        request.setToken("26227159");

        request.setId(1063L);

        DetailsResponse resp = this.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
