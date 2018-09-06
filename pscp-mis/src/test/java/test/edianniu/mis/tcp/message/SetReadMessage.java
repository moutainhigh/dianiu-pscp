package test.edianniu.mis.tcp.message;

import com.edianniu.pscp.mis.bean.request.message.SetReadMessageRequest;
import com.edianniu.pscp.mis.bean.response.message.SetReadMessageResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: SetReadMessage
 * Author: tandingbo
 * CreateTime: 2017-05-04 17:32
 */
public class SetReadMessage extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SetReadMessage.class);

    @Test
    public void setReadMessageTest() throws IOException {
        SetReadMessageRequest request = new SetReadMessageRequest();
        request.setUid(1039L);
        request.setToken("83299249");
        request.setId(1002L);
        SetReadMessageResponse response = this.sendRequest(request, 1002041, SetReadMessageResponse.class);
        log.info("resp:{}", response);
    }
}
