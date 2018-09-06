package test.edianniu.cs.tcp.engineeringproject;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.engineeringproject.ListRequest;
import com.edianniu.pscp.cs.bean.response.engineeringproject.ListResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 项目列表
 * @author zhoujianjian
 * 2017年9月19日下午4:32:49
 */
public class ProjectList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ProjectList.class);
    private int msgcode = 1002153;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1250L);
        request.setToken("79449236");
        request.setOffset(0);
        //request.setStatus("progressing");
       
        
        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
