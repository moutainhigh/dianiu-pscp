package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.SaveRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.SaveResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.*;

/**
 * ClassName: Save
 * Author: tandingbo
 * CreateTime: 2017-06-29 14:15
 */
public class Save extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Details.class);
    private int msgcode = 1002103;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        // 请求参数构造
        buildSaveSocialWorkOrderReqInfo(request);

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

    private void buildSaveSocialWorkOrderReqInfo(SaveRequest request) {
        request.setOrderId("TEST201707271114");

        java.util.List<RecruitInfo> recruitInfoList = new ArrayList<>();

        RecruitInfo recruitInfo = new RecruitInfo();
        /**
         * 电工资质
         */
        recruitInfo.setQualifications("[{\"id\": 1003}]");
        /**
         * 电工人数
         */
        recruitInfo.setQuantity(2);
        /**
         * 费用
         */
        recruitInfo.setFee(1D);
        /**
         * 工作标题
         */
        recruitInfo.setTitle("招募接口测试20170727-01");
        /**
         * 需求描述
         */
        recruitInfo.setContent("招募接口测试,数据仅用在本地接口20170727-01");
        /**
         * 开始工作时间
         */
        recruitInfo.setBeginWorkTime("2017-07-28");
        /**
         * 结束工作时间
         */
        recruitInfo.setEndWorkTime("2017-07-29");
        /**
         * 派单截止时间
         */
        recruitInfo.setExpiryTime("2017-07-27");

        recruitInfoList.add(recruitInfo);

        recruitInfo = new RecruitInfo();
        /**
         * 电工资质
         */
        recruitInfo.setQualifications("[{\"id\": 1003}]");
        /**
         * 电工人数
         */
        recruitInfo.setQuantity(2);
        /**
         * 费用
         */
        recruitInfo.setFee(1D);
        /**
         * 工作标题
         */
        recruitInfo.setTitle("招募接口测试20170727-02");
        /**
         * 需求描述
         */
        recruitInfo.setContent("招募接口测试,数据仅用在本地接口20170727-02");
        /**
         * 开始工作时间
         */
        recruitInfo.setBeginWorkTime("2017-08-01");
        /**
         * 结束工作时间
         */
        recruitInfo.setEndWorkTime("2017-08-05");
        /**
         * 派单截止时间
         */
        recruitInfo.setExpiryTime("2017-07-30");

        recruitInfoList.add(recruitInfo);
        request.setRecruitInfoList(recruitInfoList);
    }
}
