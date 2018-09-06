package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.request.workOrder.SaveRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.SaveResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.ElectricianWorkOrderInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;
import java.util.*;

/**
 * ClassName: Save
 * Author: tandingbo
 * CreateTime: 2017-06-28 16:17
 */
public class Save extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Save.class);
    private int msgcode = 1002097;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        // 请求参数构造
        buildSaveWorkOrderReqInfo(request);

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

    private void buildSaveWorkOrderReqInfo(SaveRequest request) {
        /**
         * 工单类型
         */
        request.setType(2);
        /**
         * 工单ID（不为空为修改）
         */
        request.setId(1362L);
        /**
         * 工单编号
         */
        request.setOrderId("GD91049557910162017");
        /**
         * 工单名称
         */
        request.setName("嘻嘻1要招募社会电工");
        /**
         * 检修——开始时间
         */
        request.setStartTime("2017-07-25");
        /**
         * 检修——结束时间
         */
        request.setEndTime("2017-07-27");
        /**
         * 工作地点
         */
        request.setAddress("嘻嘻1工作地点");
        /**
         * 经度
         */
        request.setLatitude(30.240495D);
        /**
         * 纬度
         */
        request.setLongitude(120.171259D);
        /**
         * 负责人ID
         */
        request.setLeaderId(1206L);
        /**
         * 工单描述
         */
        request.setContent("嘻嘻1工单描述");
        /**
         * 危险有害因数辨识(存储json格式)
         */
        request.setIdentifications("危险有害因数辨识(存储json格式)");
        /**
         * 危险有害因数辨识(业主单位负责人填写内容）
         */
        request.setIdentificationText("危险有害因数辨识(业主单位负责人填写内容）");
        /**
         * 安全措施(存储json格式)
         */
        request.setSafetyMeasures("安全措施(存储json格式)");
        /**
         * 安全措施其他（业主单位负责人填写内容）
         */
        request.setMeasureText("安全措施其他（业主单位负责人填写内容）");
        /**
         * 携带机械或设备(存储json格式)
         */
        request.setToolequipmentInfo("");
        /**
         * 检修项目与电工信息
         */
        java.util.List<ElectricianWorkOrderInfo> electricianWorkOrderInfoList = new ArrayList<>();
        ElectricianWorkOrderInfo electricianWorkOrderInfo = new ElectricianWorkOrderInfo();
        electricianWorkOrderInfo.setCheckOption("嘻嘻1检修项目");
        // 不为空为修改
        electricianWorkOrderInfo.setCheckOptionId("");
        java.util.List<Long> electricianIdList = new ArrayList<>();
        electricianIdList.add(1206L);
        electricianWorkOrderInfo.setElectricianIdList(electricianIdList);
        electricianWorkOrderInfoList.add(electricianWorkOrderInfo);
        request.setElectricianWorkOrderInfoList(electricianWorkOrderInfoList);
        /**
         * 项目信息
         */
        ProjectVO projectInfo = new ProjectVO();
        projectInfo.setId(1017L);
        projectInfo.setName("");
        request.setProjectInfo(projectInfo);
    }
}
