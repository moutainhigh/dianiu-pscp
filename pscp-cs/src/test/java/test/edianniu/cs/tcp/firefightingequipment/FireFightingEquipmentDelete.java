package test.edianniu.cs.tcp.firefightingequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.firefightingequipment.DeleteRequest;
import com.edianniu.pscp.cs.bean.response.firefightingequipment.DeleteResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 消防设施删除
 * @author zhoujianjian
 * @date 2017年11月2日 上午10:03:32
 */
public class FireFightingEquipmentDelete extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(FireFightingEquipmentDelete.class);
    private int msgcode = 1002138;

    @Test
    public void test() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        
        request.setId(1006L);
        
        DeleteResponse resp = super.sendRequest(request, msgcode, DeleteResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
