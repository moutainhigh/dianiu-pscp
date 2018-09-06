package test.edianniu.cs.tcp.firefightingequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.firefightingequipment.SaveRequest;
import com.edianniu.pscp.cs.bean.response.firefightingequipment.SaveResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 消防设施保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午6:32:51
 */
public class FireFightingEquipmentSave extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(FireFightingEquipmentSave.class);
    private int msgcode = 1002140;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        request.setRoomId(1026L);
        request.setName("消防设备2");
        request.setBoxNumber("22222");
        request.setSerialNumber("sarhgdfgd");
        request.setPlacementPosition("lkhkulg");
        request.setSpecifications("sadarh");
        request.setIndoorOrOutdoor(1);
        request.setProductionDate("2017-09-08");
        request.setExpiryDate("2027-09-07");
        request.setViewCycle(10);
        request.setViewTimeUnit(2);
        request.setInitialTestDate("2017-11-12");

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
