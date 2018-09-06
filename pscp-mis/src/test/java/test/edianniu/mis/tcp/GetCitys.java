package test.edianniu.mis.tcp;

import com.edianniu.pscp.mis.bean.request.GetCitysRequest;
import com.edianniu.pscp.mis.bean.response.GetCitysResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class GetCitys extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(GetCitys.class);
    private int msgcode = 1002057;

    @Test
    public void test() throws IOException {

        GetCitysRequest request = new GetCitysRequest();
        request.setUid(1207L);
        request.setToken("74961151");
        request.setProvinceId(1L);

        GetCitysResponse response = this.sendRequest(request, msgcode, GetCitysResponse.class);
        log.info("resp:", response);
    }

}
