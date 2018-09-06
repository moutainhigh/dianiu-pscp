/**
 *
 */
package test.edianniu.cs.dubbo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.power.AllWarningsReqData;
import com.edianniu.pscp.cs.bean.power.CollectorPointsReqData;
import com.edianniu.pscp.cs.bean.power.CollectorPointsResult;
import com.edianniu.pscp.cs.bean.power.ConsumeRankReqData;
import com.edianniu.pscp.cs.bean.power.ConsumeRankResult;
import com.edianniu.pscp.cs.bean.power.CurrentBalanceReqData;
import com.edianniu.pscp.cs.bean.power.CurrentBalanceResult;
import com.edianniu.pscp.cs.bean.power.GeneralByTypeReqData;
import com.edianniu.pscp.cs.bean.power.GeneralByTypeResult;
import com.edianniu.pscp.cs.bean.power.HasDataResult;
import com.edianniu.pscp.cs.bean.power.MonitorListReqData;
import com.edianniu.pscp.cs.bean.power.MonitorListResult;
import com.edianniu.pscp.cs.bean.power.RealTimeLoadReqData;
import com.edianniu.pscp.cs.bean.power.RealTimeLoadResult;
import com.edianniu.pscp.cs.bean.power.SafetyVoltageReqData;
import com.edianniu.pscp.cs.bean.power.SafetyVoltageResult;
import com.edianniu.pscp.cs.bean.power.UseFengGUReqData;
import com.edianniu.pscp.cs.bean.power.UseFengGUResult;
import com.edianniu.pscp.cs.bean.power.WarningListResult;
import com.edianniu.pscp.cs.bean.power.WarningSaveReqData;
import com.edianniu.pscp.cs.bean.power.WarningSaveResult;
import com.edianniu.pscp.cs.bean.room.RoomListResult;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.service.dubbo.PowerInfoService;
import com.edianniu.pscp.cs.service.dubbo.ProjectInfoService;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import java.io.IOException;
import java.util.List;

/**
 * @author cyl
 */
public class TestDubbo {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:Test.xml");
        testGetProjectIds(ctx);
    }
    
    public static void testGetProjectIds(ApplicationContext ctx){
    	ProjectInfoService projectInfoService = (ProjectInfoService) ctx.getBean("projectInfoService");
    	List<Long> projectIds = projectInfoService.getProjectIds(1250L);
    	System.out.println(projectIds);
    }
    
    public static void testGetRoomsByCustomerId(ApplicationContext ctx){
    	RoomInfoService roomInfoService = (RoomInfoService) ctx.getBean("roomInfoService");
    	
    	Long companyId = 1086L;
		Long customerId = 1042L;
		RoomListResult result = roomInfoService.getRoomsByCustomerId(customerId, companyId);
		
		System.out.println(JSON.toJSONString(result));
		
		List<RoomVO> roomList = result.getRoomList();
		for (RoomVO roomVO : roomList) {
			System.out.println(JSON.toJSONString(roomVO));
		}
    }
    
    public static void testWarningSave(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	
    	WarningSaveReqData reqData = new WarningSaveReqData();
    	reqData.setUid(1250L);
    	reqData.setWarningType("企业掉线");
    	reqData.setWarningObject("火灾演示箱");
    	reqData.setOccurTime("2017-12-28 12:22:22");
    	reqData.setDescription("企业掉线");
    	
    	WarningSaveResult saveWarning = powerInfoService.saveWarning(reqData);
    	String resultMessage = saveWarning.getResultMessage();
    	System.out.println(resultMessage);
    }
    
    public static void getmonitorList(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	
    	MonitorListReqData reqData = new MonitorListReqData();
    	reqData.setUid(1184L);
    	reqData.setLimit(10);
    	reqData.setOffset(0);
		MonitorListResult listResult = powerInfoService.getmonitorList(reqData);
		
		String jsonString = JSON.toJSONString(listResult);
		System.out.println(jsonString);
    } 
    
    public static void getRealTimeLoad(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	RealTimeLoadReqData req = new RealTimeLoadReqData();
    	req.setUid(1344L);
    	req.setDate("2018-01-15");
    	RealTimeLoadResult result = powerInfoService.getRealTimeLoad(req);
    	String jsonString = JSON.toJSONString(result);
    	System.out.println(jsonString);
    }
    
    public static void getGeneralByType(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	GeneralByTypeReqData rq = new GeneralByTypeReqData();
    	rq.setUid(1243L);
    	rq.setType(1);
    	//rq.setDate("2018-01-16");
    	GeneralByTypeResult result = powerInfoService.getGeneralByType(rq);
    	String jsonString = JSON.toJSONString(result);
    	System.out.println(jsonString);
    }
    
    public static void getAllWarnings(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	AllWarningsReqData req = new AllWarningsReqData();
    	req.setUid(1272L);
    	WarningListResult result = powerInfoService.getAllWarnings(req);
    	String jsonString = JSON.toJSONString(result);
    	System.out.println(jsonString);
    }
    
    public static void getConsumeRank(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	ConsumeRankReqData reqData = new ConsumeRankReqData();
    	reqData.setUid(1243L);
    	reqData.setDate("2018-01");
		ConsumeRankResult result = powerInfoService.getConsumeRank(reqData);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
    }
    
    public static void getSafetyVoltage(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	SafetyVoltageReqData req = new SafetyVoltageReqData();
    	//req.setUid(1383L);
    	req.setUid(1382L);
		SafetyVoltageResult result = powerInfoService.getSafetyVoltage(req);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
    }
    
    public static void getCurrentBalance(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	CurrentBalanceReqData reqData = new CurrentBalanceReqData();
    	reqData.setUid(1344L);
		CurrentBalanceResult result = powerInfoService.getCurrentBalance(reqData);
    	String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
    }
    
    public static void useFengGU(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	UseFengGUReqData reqData = new UseFengGUReqData();
    	reqData.setUid(1243L);
		UseFengGUResult result = powerInfoService.useFengGU(reqData);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
    }
    
    public static void getCollectorPointsForPowerFactor(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	CollectorPointsReqData reqData = new CollectorPointsReqData();
    	reqData.setUid(1243L);
		CollectorPointsResult result = powerInfoService.getCollectorPointsForPowerFactor(reqData);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
    }
    
    public static void getCollectorPointsForPowerQuantity(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	CollectorPointsReqData reqData = new CollectorPointsReqData();
    	reqData.setUid(1243L);
    	reqData.setStartTime("15:00:00");
    	reqData.setEndTime("15:15:00");
		CollectorPointsResult result = powerInfoService.getCollectorPointsForPowerQuantity(reqData);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
    }
    
    public static void hasData(ApplicationContext ctx){
    	PowerInfoService powerInfoService = (PowerInfoService) ctx.getBean("powerInfoService");
    	Long companyId = 1083L;
		String meterId = "1234";
		HasDataResult result = powerInfoService.hasData(meterId, companyId);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
    }
    
}
