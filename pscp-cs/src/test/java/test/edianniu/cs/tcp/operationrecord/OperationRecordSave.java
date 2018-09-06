package test.edianniu.cs.tcp.operationrecord;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.operationrecord.SaveRequest;
import com.edianniu.pscp.cs.bean.response.operationrecord.SaveResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房客户操作记录新增与编辑
 * @author zhoujianjian
 * @date 2017年10月19日 上午10:39:39
 */
public class OperationRecordSave extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(OperationRecordSave.class);
    private int msgcode = 1002151;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        //request.setId(1000L);
        request.setRoomId(1031L);
        request.setEquipmentTask("机房检查2");
        request.setGroundLeadNum("0002");
        request.setStartTime("2017-10-19");
        request.setEndTime("2017-10-21");
        request.setIssuingCommand("张三");
        request.setReceiveCommand("李四");
        request.setOperator("王五");
        request.setGuardian("赵六");
        request.setRemark("机房检查机房检查");
        

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
