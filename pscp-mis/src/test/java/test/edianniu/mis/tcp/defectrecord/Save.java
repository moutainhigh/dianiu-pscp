package test.edianniu.mis.tcp.defectrecord;

import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.request.defectrecord.SaveRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.SaveResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: Save
 * Author: tandingbo
 * CreateTime: 2017-09-12 16:01
 */
public class Save extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Save.class);
    private int msgcode = 1002157;

    @Test
    public void test() throws IOException {

        SaveRequest request = new SaveRequest();
        request.setUid(1356L);
        request.setToken("26227159");

        request.setOrderId("GD2362717522817535");
        request.setRoomId(1055L);
        request.setDeviceName("测试图片上传");
        request.setDefectContent("代码测试内容2");
        request.setDiscoveryDate("2017-09-14");
        request.setDiscoverer("tanbobo");
        request.setDiscoveryCompany("代码测试公司名称2");
        request.setContactNumber("1234567890");
        request.setRemark("代码测试备注2");
        
        List<CommonAttachment> attachmentList = new ArrayList<CommonAttachment>();
        CommonAttachment a1 = new CommonAttachment();
        a1.setFid("111111111111");
        a1.setOrderNum(1L);
        a1.setType(1);
        CommonAttachment a2 = new CommonAttachment();
        a2.setFid("2222222222222");
        a2.setOrderNum(2L);
        a2.setType(1);
        attachmentList.add(a1);
        attachmentList.add(a2);
        
		request.setAttachmentList(attachmentList);

        SaveResponse resp = this.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}