package test.edianniu.cs.tcp.dutylog;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.dutylog.SaveRequest;
import com.edianniu.pscp.cs.bean.response.dutylog.SaveResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房客户操作记录新增与编辑
 * @author zhoujianjian
 * @date 2017年10月19日 上午10:39:39
 */
public class DutyLogSave extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DutyLogSave.class);
    private int msgcode = 1002141;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        request.setId(1000L);
        request.setRoomId(1031L);
        request.setTitle("test111");
        request.setContent("testtest1111");
        
        /*List<Attachment> list = new ArrayList<>();
        Attachment attachment1 = new Attachment();
        attachment1.setFid("111");
        list.add(attachment1);
        request.setAttachmentList(list);*/
        
        request.setRemovedImgs("1494");

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
