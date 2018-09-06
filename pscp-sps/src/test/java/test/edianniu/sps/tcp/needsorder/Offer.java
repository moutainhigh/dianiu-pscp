package test.edianniu.sps.tcp.needsorder;

import com.edianniu.pscp.sps.bean.request.needsorder.OfferRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.OfferResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: Offer
 * Author: tandingbo
 * CreateTime: 2017-09-26 14:16
 */
public class Offer extends AbstractLocalTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private int msgcode = 1002172;

    @Test
    public void test() throws IOException {
        OfferRequest request = new OfferRequest();
        request.setUid(1185L);
        request.setToken("32581897");

        // 请求参数构造
        buildLiquidateReqInfo(request);

        OfferResponse resp = super.sendRequest(request, msgcode, OfferResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

    private void buildLiquidateReqInfo(OfferRequest request) {
        request.setOrderId("NEO46519876629299");
        request.setQuotedPrice("1000.0");

        List<Map<String, Object>> attachmentList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("fid", "1x1x1x1xx1x1");
        attachmentList.add(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("fid", "2x2x2x2x2x2x2x2x2");
        map2.put("orderNum", 1);
        attachmentList.add(map2);
        request.setAttachmentList(attachmentList);
    }

}
