package test.edianniu.cs.tcp.safetyequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.safetyequipment.SaveRequest;
import com.edianniu.pscp.cs.bean.response.safetyequipment.SaveResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房客户操作记录新增与编辑
 * @author zhoujianjian
 * @date 2017年10月19日 上午10:39:39
 */
public class SafetyEquipmentSave extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SafetyEquipmentSave.class);
    private int msgcode = 1002132;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        request.setRoomId(1025L);
        request.setName("安全设备");
        request.setSerialNumber("1234454");
        request.setModelNumber("BBB111111");
        request.setVoltageLevel("21级");
        request.setManufacturer("杭州电牛");
        request.setTestCycle(5);
        request.setTestTimeUnit(1);
        request.setInitialTestDate("2017-10-31");
        request.setRemark("test");

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
