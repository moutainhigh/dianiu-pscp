package test.edianniu.mis.tcp.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.request.worksheetreport.survey.RemoveRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.survey.RemoveResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * @author tandingbo
 * @create 2017-11-08 17:41
 */
public class Remove extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Remove.class);
    private int msgcode = 1002181;

    @Test
    public void test() throws IOException {

        RemoveRequest request = new RemoveRequest();
        request.setUid(1185L);
        request.setToken("26227159");

        request.setId(1036L);

        RemoveResponse resp = this.sendRequest(request, msgcode, RemoveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
