package test.edianniu.mis.tcp.message;

import com.edianniu.pscp.mis.bean.request.message.ClearAllMessageRequest;
import com.edianniu.pscp.mis.bean.response.message.ClearAllMessageResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: ClearAllMessage
 * Author: tandingbo
 * CreateTime: 2017-05-04 17:35
 */
public class ClearAllMessage extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ClearAllMessage.class);

    @Test
    public void clearAllMessageTest() throws IOException {
        ClearAllMessageRequest request = new ClearAllMessageRequest();
        request.setUid(1039L);
        request.setToken("83299249");
        ClearAllMessageResponse response = this.sendRequest(request, 1002042, ClearAllMessageResponse.class);
        log.info("resp:{}", response);
    }
}
