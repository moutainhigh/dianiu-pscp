package test.edianniu.sps.tcp;

import com.edianniu.pscp.sps.bean.request.ConfigureParamRequest;
import com.edianniu.pscp.sps.bean.response.ConfigureParamResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * ClassName: ConfigureParam
 * Author: tandingbo
 * CreateTime: 2017-04-26 15:50
 */
public class ConfigureParam extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ConfigureParam.class);
    private int msgcode = 1002050;

    @Test
    public void test() throws IOException {
        ConfigureParamRequest request = new ConfigureParamRequest();
        request.setUid(1039L);
        request.setToken("57388449");

        ConfigureParamResponse resp = super.sendRequest(request, msgcode, ConfigureParamResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
