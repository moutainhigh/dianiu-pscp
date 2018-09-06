package test.edianniu.mis.tcp.invoice;

import com.edianniu.pscp.mis.bean.request.invoice.info.DetailInvoiceInfoRequest;
import com.edianniu.pscp.mis.bean.response.invoice.info.DetailInvoiceInfoResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class InvoiceInfoDetailTest extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InvoiceInfoDetailTest.class);

    @Test
    public void detailInvoiceInfo() throws IOException {
        DetailInvoiceInfoRequest request = new DetailInvoiceInfoRequest();
        request.setOrderId("I2920926480141");
        request.setUid(1243L);
        DetailInvoiceInfoResponse resp = this.sendRequest(request, 1002196, DetailInvoiceInfoResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
