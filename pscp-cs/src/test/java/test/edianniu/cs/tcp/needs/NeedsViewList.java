package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.needs.NeedsViewListRequest;
import com.edianniu.pscp.cs.bean.response.needs.NeedsViewListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 需求列表 后台使用
 * @author zhoujianjian
 * 2017年9月21日下午11:51:01
 */
public class NeedsViewList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsViewList.class);
    private int msgcode = 1002155;

    @Test
    public void test() throws IOException {
        NeedsViewListRequest request = new NeedsViewListRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        request.setOffset(0);
        
      //需求状态(后台)："not_audit"（未审核） "audit_succeed"（审核通过）   "audit_failed"（审核未通过）
        request.setStatus("not_audit");
        //request.setMemberName("勇");
        //request.setLoginId("18566666666");
        //request.setNeedsName("需求");
        
        NeedsViewListResponse resp = super.sendRequest(request, msgcode, NeedsViewListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
