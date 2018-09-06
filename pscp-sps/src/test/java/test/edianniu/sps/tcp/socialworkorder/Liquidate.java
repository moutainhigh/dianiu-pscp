package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;
import com.edianniu.pscp.sps.bean.request.socialworkorder.LiquidateRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.LiquidateResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: LiquidateDetails
 * Author: tandingbo
 * CreateTime: 2017-06-30 11:24
 */
public class Liquidate extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Liquidate.class);
    private int msgcode = 1002107;

    @Test
    public void test() throws IOException {
        LiquidateRequest request = new LiquidateRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        // 请求参数构造
        buildLiquidateReqInfo(request);

        LiquidateResponse resp = super.sendRequest(request, msgcode, LiquidateResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

    private void buildLiquidateReqInfo(LiquidateRequest request) {
        request.setElectricianId(1199L);
        request.setSocialWorkOrderId(1413L);
        request.setElectricianWorkOrderId(1835L);
        request.setActualWorkTime(3.1D);
        EvaluateVO evaluateVO = new EvaluateVO();
        evaluateVO.setServiceSpeed(5);
        evaluateVO.setServiceQuality(5);
        evaluateVO.setContent("优秀");
        request.setEvaluateInfo(evaluateVO);
    }
}
