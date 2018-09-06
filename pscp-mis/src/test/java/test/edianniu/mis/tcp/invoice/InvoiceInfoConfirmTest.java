package test.edianniu.mis.tcp.invoice;

import com.edianniu.pscp.mis.bean.request.invoice.info.ConfirmInvoiceInfoRequest;
import com.edianniu.pscp.mis.bean.response.invoice.info.ConfirmInvoiceInfoResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class InvoiceInfoConfirmTest extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InvoiceInfoConfirmTest.class);

    @Test
    public void confirmInvoiceInfo() throws IOException {
        ConfirmInvoiceInfoRequest request = new ConfirmInvoiceInfoRequest();
        request.setOrderId("I2920924423611");
        request.setUid(1243L);
        ConfirmInvoiceInfoResponse resp = this.sendRequest(request, 1002197, ConfirmInvoiceInfoResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
