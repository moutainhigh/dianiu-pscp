package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.ConfirmRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.ConfirmResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.*;

/**
 * ClassName: Confirm
 * Author: tandingbo
 * CreateTime: 2017-06-29 16:54
 */
public class Confirm extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Confirm.class);
    private int msgcode = 1002105;

    @Test
    public void test() throws IOException {
        ConfirmRequest request = new ConfirmRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        // 请求参数构造
        buildConfirmSocialWorkOrderReqInfo(request);

        ConfirmResponse resp = super.sendRequest(request, msgcode, ConfirmResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

    private void buildConfirmSocialWorkOrderReqInfo(ConfirmRequest request) {
        request.setOrderId("SGD35858020407920");
        java.util.List<Map<String, Object>> socialElectricianList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("auditing", "unpass");
        map.put("electricianId", 1199L);
        map.put("electricianWorkOrderId", 1707L);
        socialElectricianList.add(map);

        request.setSocialElectricianList(socialElectricianList);
    }
}
