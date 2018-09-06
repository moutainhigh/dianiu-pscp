package test.edianniu.cs.tcp.safetyequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.safetyequipment.ListRequest;
import com.edianniu.pscp.cs.bean.response.safetyequipment.ListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房安全用具列表
 * @author zhoujianjian
 * @date 2017年10月31日 下午9:46:31
 */
public class SafetyEquipmentList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SafetyEquipmentList.class);
    private int msgcode = 1002131;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        request.setOffset(0);
        
        request.setRoomId(-1L);
       
        
        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
