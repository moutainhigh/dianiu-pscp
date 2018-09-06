package test.edianniu.cs.tcp.workorder;

import com.edianniu.pscp.cs.bean.request.workorder.EvaluateRequest;
import com.edianniu.pscp.cs.bean.response.workorder.EvaluateResponse;
import com.edianniu.pscp.cs.bean.workorder.EvaluateInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: WorkOrderEvaluate
 * Author: tandingbo
 * CreateTime: 2017-08-15 09:41
 */
public class WorkOrderEvaluate extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(WorkOrderEvaluate.class);
    private int msgcode = 1002117;

    @Test
    public void test() throws IOException {
        EvaluateRequest request = new EvaluateRequest();
        request.setUid(1201L);
        request.setToken("79449236");


        request.setOrderId("GD8037356232684");

        // 评价信息
        EvaluateInfo evaluateInfo = new EvaluateInfo();
        evaluateInfo.setContent("优秀");
        evaluateInfo.setServiceQuality(5);
        evaluateInfo.setServiceSpeed(5);
        List<String> evaluateImageList = new ArrayList<>();
        evaluateImageList.add("image1.jpg");
        evaluateImageList.add("image2.jpg");
        evaluateImageList.add("image3.jpg");
        evaluateInfo.setEvaluateImageArray(evaluateImageList.toArray(new String[]{}));
        request.setEvaluateInfo(evaluateInfo);

        EvaluateResponse resp = super.sendRequest(request, msgcode, EvaluateResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
