package test.edianniu.mis.tcp.invoice;

import com.edianniu.pscp.mis.bean.request.invoice.title.InvoiceTitleDeleteRequest;
import com.edianniu.pscp.mis.bean.response.invoice.title.InvoiceTitleDeleteResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class InvoiceTitleDeleteTest extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InvoiceTitleDeleteTest.class);

    @Test
    public void getInvoiceTitle() throws IOException {
        InvoiceTitleDeleteRequest request = new InvoiceTitleDeleteRequest();
        request.setId(1000L);
        request.setUid(1L);
        InvoiceTitleDeleteResponse resp = this.sendRequest(request, 1002194, InvoiceTitleDeleteResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
