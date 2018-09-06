package test.edianniu.cs.tcp.safetyequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.safetyequipment.DeleteRequest;
import com.edianniu.pscp.cs.bean.response.safetyequipment.DeleteResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房安全用具删除
 * @author zhoujianjian
 * @date 2017年11月1日 下午2:57:13
 */
public class SafetyEquipmentDelete extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SafetyEquipmentDelete.class);
    private int msgcode = 1002134;

    @Test
    public void test() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        
        request.setId(1001L);
        
        DeleteResponse resp = super.sendRequest(request, msgcode, DeleteResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
