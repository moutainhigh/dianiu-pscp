package test.edianniu.mis.tcp.invoice;

import com.edianniu.pscp.mis.bean.request.invoice.info.ListInvoiceInfoRequest;
import com.edianniu.pscp.mis.bean.response.invoice.info.ListInvoiceInfoResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class InvoiceInfoListTest extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InvoiceInfoListTest.class);
    private int msgcodeList = 1002195;


    @Test
    public void listInvoiceInfo() throws IOException {
        ListInvoiceInfoRequest request = new ListInvoiceInfoRequest();
        request.setOffset(0);
        request.setStatus(1);
        request.setUid(1243L);
        request.setPayDate("2018-04-20");
        ListInvoiceInfoResponse resp = this.sendRequest(request, msgcodeList, ListInvoiceInfoResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }


}
