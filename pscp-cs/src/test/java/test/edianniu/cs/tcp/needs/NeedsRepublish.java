package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.needs.RepublishRequest;
import com.edianniu.pscp.cs.bean.response.needs.RepublishResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 需求重新發佈
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class NeedsRepublish extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsRepublish.class);
    private int msgcode = 1002123;

    @Test
    public void test() throws IOException {
        RepublishRequest request = new RepublishRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        
        request.setOrderId("NE1899872686668708");
        request.setName("101号配电房维护");
        request.setDescribe("101号配电房维护");
        
//        List<Attachment> attachementList = new ArrayList<Attachment>();
//        Attachment attachment1 = new Attachment();
//        attachment1.setfId("c333333");
//        attachment1.setOrderNum(3);
//        Attachment attachment2 =new Attachment();
//        attachment2.setfId("d44444");
//        attachment2.setOrderNum(4);
//        attachementList.add(attachment1);
//        attachementList.add(attachment2);
//		  request.setAttachementList(attachementList);
		
        request.setPublishCutoffTime("2017-10-26");
        request.setWorkingStartTime("2017-10-27");
        request.setWorkingEndTime("2017-10-28");
       
        request.setContactPerson("王斌");
        request.setContactNumber("18666775687");
        request.setContactAddr("文西二路");
        request.setDistributionRoomIds("1040,1033");
        
        RepublishResponse resp = super.sendRequest(request, msgcode, RepublishResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
