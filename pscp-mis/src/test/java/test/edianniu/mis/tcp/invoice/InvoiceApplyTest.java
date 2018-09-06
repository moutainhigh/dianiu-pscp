package test.edianniu.mis.tcp.invoice;

import com.edianniu.pscp.mis.bean.request.invoice.ApplyInvoiceRequest;
import com.edianniu.pscp.mis.bean.response.invoice.ApplyInvoiceResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class InvoiceApplyTest extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InvoiceApplyTest.class);

    @Test
    public void getInvoiceTitle() throws IOException {
        ApplyInvoiceRequest request = new ApplyInvoiceRequest();

        request.setUid(1563L);
        request.setPayOrderId("RD4838844880103453");
        request.setInvoiceTitleId(1022L);

        ApplyInvoiceResponse resp = this.sendRequest(request, 1002198, ApplyInvoiceResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
