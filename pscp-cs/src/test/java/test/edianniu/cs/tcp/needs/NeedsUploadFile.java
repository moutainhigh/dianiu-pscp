package test.edianniu.cs.tcp.needs;

import com.edianniu.pscp.cs.bean.request.needs.UploadFileRequest;
import com.edianniu.pscp.cs.bean.response.needs.UploadFileResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 合作附件上传、编辑
 *
 * @author zhoujianjian
 * 2017年9月18日下午4:11:56
 */
public class NeedsUploadFile extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsUploadFile.class);
    private int msgcode = 1002129;

    @Test
    public void test() throws IOException {
        UploadFileRequest request = new UploadFileRequest();
        request.setUid(1187L);
        request.setToken("79449236");

        request.setOrderId("NE2095272538431642");
        request.setRemovedImgs("1403,1404");
        


        UploadFileResponse resp = super.sendRequest(request, msgcode, UploadFileResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
