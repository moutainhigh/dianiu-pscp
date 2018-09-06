package test.edianniu.mis.tcp.invoice;

import com.edianniu.pscp.mis.bean.request.invoice.title.InvoiceTitleRequest;
import com.edianniu.pscp.mis.bean.response.invoice.title.InvoiceTitleResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class InvoiceTitleTest extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InvoiceTitleTest.class);

    @Test
    public void getInvoiceTitle() throws IOException {
        InvoiceTitleRequest request = new InvoiceTitleRequest();
        request.setUid(1243L);
        InvoiceTitleResponse resp = this.sendRequest(request, 1002192, InvoiceTitleResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
