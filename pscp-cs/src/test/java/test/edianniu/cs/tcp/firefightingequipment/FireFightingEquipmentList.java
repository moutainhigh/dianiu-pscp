package test.edianniu.cs.tcp.firefightingequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.firefightingequipment.ListRequest;
import com.edianniu.pscp.cs.bean.response.firefightingequipment.ListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 消防设施列表
 * @author zhoujianjian
 * @date 2017年11月1日 下午7:25:02
 */
public class FireFightingEquipmentList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(FireFightingEquipmentList.class);
    private int msgcode = 1002139;

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
