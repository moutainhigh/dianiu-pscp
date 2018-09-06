package test.edianniu.mis.tcp.message;

import com.edianniu.pscp.mis.bean.request.message.ListMessageRequest;
import com.edianniu.pscp.mis.bean.response.message.ListMessageResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: ListMessage
 * Author: tandingbo
 * CreateTime: 2017-05-04 17:11
 */
public class ListMessage extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ListMessage.class);

    @Test
    public void listMessage() throws IOException {
        ListMessageRequest request = new ListMessageRequest();
        request.setUid(1243L);
        request.setToken("32631456");
        request.setOffset(0);
        request.setType("message");
        ListMessageResponse response = this.sendRequest(request, 1002040, ListMessageResponse.class);
        log.info("resp:{}", response);
    }

}
