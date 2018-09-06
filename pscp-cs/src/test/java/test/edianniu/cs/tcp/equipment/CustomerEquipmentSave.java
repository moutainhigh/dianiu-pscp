package test.edianniu.cs.tcp.equipment;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.equipment.SaveRequest;
import com.edianniu.pscp.cs.bean.response.equipment.SaveResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房设备新增
 * @author zhoujianjian
 * 2017年9月29日下午3:11:56
 */
public class CustomerEquipmentSave extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerEquipmentSave.class);
    private int msgcode = 1002146;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        //request.setUid(1187L);
        request.setUid(1226L);
        request.setToken("79449236");
        //request.setEquipmentId(10011L);
        
        request.setRoomId(1031L);
        request.setName("电机1");
        request.setProductionDate("2017-05-10");
        request.setSerialNumber("L333333");
        request.setManufacturer("杭州");
        request.setModel("xx99999");
        

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
