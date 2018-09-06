package test.edianniu.mis.tcp.worksheetreport.repairtest;

import com.edianniu.pscp.mis.bean.request.worksheetreport.repairtest.SaveRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.repairtest.SaveResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Save
 * Author: tandingbo
 * CreateTime: 2017-09-13 15:37
 */
public class Save extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Save.class);
    private int msgcode = 1002162;

    @Test
    public void test() throws IOException {

        SaveRequest request = new SaveRequest();
        request.setUid(1185L);
        request.setToken("26227159");

        request.setOrderId("GD38941505536068");
        request.setDeviceName("测试修试记录1");
        request.setWorkContent("测试修试记录内容1");
        request.setWorkDate("2017-08-06");
        request.setLeadingOfficial("tanbobo");
        request.setCompanyName("boboYun");
        request.setContactNumber("0987654321");
        request.setReceiver("测试员1");
        request.setRemark("测试修试记录备注1");

        SaveResponse resp = this.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}