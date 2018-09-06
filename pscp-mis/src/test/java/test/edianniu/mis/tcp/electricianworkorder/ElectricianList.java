package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.ElectricianListRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.ElectricianListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ElectricianList
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月25日 下午8:44:48 
 * @version V1.0
 */
public class ElectricianList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ElectricianList.class);
   
    @Test
    public void test() throws IOException {
    	ElectricianListRequest request = new ElectricianListRequest();
    	request.setUid(1060L);
    	request.setToken("22513347");
    	request.setOrderId("EGD64747760384704");

    	ElectricianListResponse resp = super.sendRequest(request, 1002046, ElectricianListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
        log.info("result resp {}", resp);
    }

}
