package test.edianniu.mis.tcp.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.request.worksheetreport.survey.SaveRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.survey.SaveResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tandingbo
 * @create 2017-11-09 10:07
 */
public class Save extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Save.class);
    private int msgcode = 1002180;

    @Test
    public void test() throws IOException {

        SaveRequest request = new SaveRequest();
        request.setUid(1356L);
        request.setToken("26227159");

        request.setOrderId("GD154931854343558");
        request.setWorkContent("勘察报告测试11111");
        
        List<CommonAttachment> attachmentList = new ArrayList<>();
        
        CommonAttachment ccCommonAttachment  = new CommonAttachment();
        ccCommonAttachment.setFid("88888");
        ccCommonAttachment.setOrderNum(1L);
        ccCommonAttachment.setType(1);
        attachmentList.add(ccCommonAttachment);
		request.setAttachmentList(attachmentList);

        SaveResponse resp = this.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
