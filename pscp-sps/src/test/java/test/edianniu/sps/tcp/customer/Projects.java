package test.edianniu.sps.tcp.customer;

import com.edianniu.pscp.sps.bean.request.customer.ProjectsRequest;
import com.edianniu.pscp.sps.bean.response.customer.ProjectsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Projects
 * Author: tandingbo
 * CreateTime: 2017-07-12 09:52
 */
public class Projects extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Projects.class);
    private int msgcode = 1002092;

    @Test
    public void test() throws IOException {
        ProjectsRequest request = new ProjectsRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        request.setCustomerId(1015L);

        ProjectsResponse resp = super.sendRequest(request, msgcode, ProjectsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
