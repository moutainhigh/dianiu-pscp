package test.edianniu.cs.tcp.equipment;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.equipment.ListRequest;
import com.edianniu.pscp.cs.bean.response.equipment.ListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房设备列表
 * @author zhoujianjian
 * 2017年9月29日下午5:36:24
 */
public class CustomerEquipmentList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerEquipmentList.class);
    private int msgcode = 1002145;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        request.setOffset(0);
        
        request.setRoomId(-1L);
       
        
        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
