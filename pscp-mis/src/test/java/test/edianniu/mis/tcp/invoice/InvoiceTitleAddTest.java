package test.edianniu.mis.tcp.invoice;

import com.edianniu.pscp.mis.bean.request.invoice.title.InvoiceTitleAddRequest;
import com.edianniu.pscp.mis.bean.response.invoice.title.InvoiceTitleAddResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class InvoiceTitleAddTest extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InvoiceTitleAddTest.class);

    @Test
    public void getInvoiceTitle() throws IOException {
        InvoiceTitleAddRequest request = new InvoiceTitleAddRequest();

        request.setUid(1404L);
        request.setContactNumber("0000000000000000001404");
        request.setBankCardNo("0000000000000000001404");
        request.setBankCardAddress("浙江省杭州市滨江区东信科技园6号楼3楼");
        request.setCompanyName("杭州电牛科技有限公司");
        request.setTaxpayerId("0000000000000000001404");

        InvoiceTitleAddResponse resp = this.sendRequest(request, 1002193, InvoiceTitleAddResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
