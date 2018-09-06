package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.electricianworkorder.FileInfo;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.AddWorkLogRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.AddWorkLogResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AddWorkLog
 * Author: tandingbo
 * CreateTime: 2017-04-26 11:24
 */
public class AddWorkLog extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(AddWorkLog.class);
    private int msgcode = 1002023;

    @Test
    public void test() throws IOException {
        AddWorkLogRequest request = new AddWorkLogRequest();
        request.setUid(1039L);
        request.setToken("57388449");
        request.setOrderId("OID-12121");
        request.setText("工作日志3");

        List<FileInfo> files = new ArrayList<>();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setType(1);
        fileInfo.setFileId("asdfasdf.jpg");
        files.add(fileInfo);

        request.setFiles(files);

        AddWorkLogResponse resp = super.sendRequest(request, msgcode, AddWorkLogResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
