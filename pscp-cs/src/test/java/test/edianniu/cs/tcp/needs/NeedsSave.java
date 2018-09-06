package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.request.needs.SaveRequest;
import com.edianniu.pscp.cs.bean.response.needs.SaveResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class NeedsSave extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsSave.class);
    private int msgcode = 1002122;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1159L);
        request.setToken("79449236");

        request.setName("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十u");
        request.setDescribe("1033号配电房维护");

        List<Attachment> attachmentList = new ArrayList<Attachment>();
        Attachment attachment1 = new Attachment();
        attachment1.setFid("a22222");
        attachment1.setOrderNum(1);
        Attachment attachment2 = new Attachment();
        attachment2.setFid("a33333");
        attachment2.setOrderNum(2);
        attachmentList.add(attachment1);
        attachmentList.add(attachment2);
        request.setAttachmentList(attachmentList);

        request.setPublishCutoffTime("2017-10-24");
        request.setWorkingStartTime("2017-10-25");
        request.setWorkingEndTime("2017-10-26");

        request.setContactPerson("王峰");
        request.setContactNumber("13243345555");
        request.setContactAddr("东信大道");
        request.setDistributionRoomIds("1023");

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
