package test.edianniu.mis.tcp.worksheetreport.patrol;

import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.request.worksheetreport.patrol.SaveRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.patrol.SaveResponse;

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
 * CreateTime: 2017-09-13 15:37
 */
public class Save extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Save.class);
    private int msgcode = 1002166;

    @Test
    public void test() throws IOException {

        SaveRequest request = new SaveRequest();
        request.setUid(1288L);
        request.setToken("26227159");

        //request.setOrderId("GD38941505536068");
        request.setCompanyName("boboYun");
        request.setContactNumber("0987654321");
        request.setDeviceName("测试巡视记录1");
        request.setWorkContent("测试巡视记录内容1");
        request.setWorkDate("2017-08-16");
        request.setLeadingOfficial("tanbobo2");
        request.setRoomId(1055L);
        request.setReceiver("测试员2");
        request.setRemark("测试巡视记录备注1");
        
        List<CommonAttachment> attachmentList = new ArrayList<CommonAttachment>();
        CommonAttachment cc = new CommonAttachment();
        cc.setFid("0000000000");
        cc.setOrderNum(1L);
        cc.setType(1);
        attachmentList.add(cc);
		request.setAttachmentList(attachmentList );

        SaveResponse resp = this.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}