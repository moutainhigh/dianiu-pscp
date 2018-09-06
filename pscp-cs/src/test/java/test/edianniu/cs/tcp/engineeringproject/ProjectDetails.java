package test.edianniu.cs.tcp.engineeringproject;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.engineeringproject.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.engineeringproject.DetailsResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 项目列表
 * @author zhoujianjian
 * 2017年9月19日下午4:32:49
 */
public class ProjectDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ProjectDetails.class);
    private int msgcode = 1002154;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        request.setProjectNo("PR2270436846841300");
       
        
        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
