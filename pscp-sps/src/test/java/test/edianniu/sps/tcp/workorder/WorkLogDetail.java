package test.edianniu.sps.tcp.workorder;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.sps.bean.request.workOrder.WorkLogDetailRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.WorkLogDetailResponse;

import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * 工作记录详情
 * @author zhoujianjian
 * @date 2018年2月7日 上午11:52:22
 */
public class WorkLogDetail extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(WorkLogDetail.class);
    private int msgcode = 1002114;

    @Test
    public void test() throws IOException {
        WorkLogDetailRequest request = new WorkLogDetailRequest();
        request.setUid(1185L);
        request.setId(1476L);

        WorkLogDetailResponse resp = super.sendRequest(request, msgcode, WorkLogDetailResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
