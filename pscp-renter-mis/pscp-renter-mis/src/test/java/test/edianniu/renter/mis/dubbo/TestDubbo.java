package test.edianniu.renter.mis.dubbo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.ConfigDetailResult;
import com.edianniu.pscp.renter.mis.bean.renter.DetailReq;
import com.edianniu.pscp.renter.mis.bean.renter.DetailResult;
import com.edianniu.pscp.renter.mis.bean.renter.ListReq;
import com.edianniu.pscp.renter.mis.bean.renter.ListResult;
import com.edianniu.pscp.renter.mis.bean.renter.RenterMeterInfo;
import com.edianniu.pscp.renter.mis.bean.renter.SaveConfigReq;
import com.edianniu.pscp.renter.mis.bean.renter.SaveReq;
import com.edianniu.pscp.renter.mis.bean.renter.SaveResult;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterInfoService;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterOrderInfoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 */
public class TestDubbo {
    
    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:Test.xml");
        tesRenterConfigDetail(ctx);
        System.exit(-1);
    }
    
    public static void tesRenterConfigDetail(ApplicationContext ctx){
		RenterInfoService renterInfoService = (RenterInfoService)ctx.getBean("renterInfoService");
		DetailReq req = new DetailReq();
		req.setRenterId(1000L);
		req.setUid(1243L);
		
		ConfigDetailResult result = renterInfoService.getRenterConfig(req);
		String string = JSON.toJSONString(result);
		System.out.println(string);
	}
    
    public static void testSaveRenter(ApplicationContext ctx){
		RenterInfoService renterInfoService = (RenterInfoService)ctx.getBean("renterInfoService");
		SaveReq req = new SaveReq();
		req.setName("ztj");
		req.setMobile("13296651501");
		req.setContract("zj");
		req.setPwd("123456");
		req.setUid(1243L);
		
		SaveResult saveRenter = renterInfoService.saveRenter(req);
		String string = JSON.toJSONString(saveRenter);
		System.out.println(string);
	}
    public static void testPayPreOrder(ApplicationContext ctx){
    	RenterOrderInfoService renterOrderInfoService = (RenterOrderInfoService)ctx.getBean("renterOrderInfoService");
    	WalletInfoService walletInfoService=(WalletInfoService)ctx.getBean("walletInfoService");
    	List<String> orderIds=renterOrderInfoService.getPrePaymentUnsettledOrders(1);
    	for(String orderId:orderIds){
    		com.edianniu.pscp.mis.bean.Result result=walletInfoService.settlementPrepayRenterChargeOrder(orderId);
    		if(!result.isSuccess()){
    			System.out.println(""+JSON.toJSONString(result));
    		}
    	}
    }
    public static void testPreOrder(ApplicationContext ctx){
    	RenterInfoService renterInfoService = (RenterInfoService)ctx.getBean("renterInfoService");
    	RenterOrderInfoService renterOrderInfoService = (RenterOrderInfoService)ctx.getBean("renterOrderInfoService");
    	List<Long> renterIds=renterInfoService.getPrePaymentRenterIds(10);
    	for(Long renterId:renterIds){
    		//首先对任务状态加锁
    		Result beforeResult=renterOrderInfoService.beforeHandleOrder(renterId);
    		if(beforeResult.isSuccess()){
    			Result result=renterOrderInfoService.handlePrePaymentOrder(renterId);
    			if(!result.isSuccess()){
    				System.out.println(JSON.toJSONString(result));
    				//遇到错误时，解锁
    				renterOrderInfoService.afterHandlerOrder(renterId);
    			}
    		}
    		
    	}
    }
    public static void testMonthlyOrder(ApplicationContext ctx){
    	RenterInfoService renterInfoService = (RenterInfoService)ctx.getBean("renterInfoService");
    	RenterOrderInfoService renterOrderInfoService = (RenterOrderInfoService)ctx.getBean("renterOrderInfoService");
    	List<Long> renterIds=renterInfoService.getMonthlyPaymentRenterIds(10);
    	for(Long renterId:renterIds){
    		Result beforeResult=renterOrderInfoService.beforeHandleOrder(renterId);
    		if(beforeResult.isSuccess()){
    			Result result=renterOrderInfoService.handleMonthlyPaymentOrder(renterId);
    			if(!result.isSuccess()){
    				System.out.println(JSON.toJSONString(result));
    				renterOrderInfoService.afterHandlerOrder(renterId);
    			}
    		}
    		
    	}
    	
    }
    public static void testRenterList(ApplicationContext ctx){
		RenterInfoService renterInfoService = (RenterInfoService)ctx.getBean("renterInfoService");
		ListReq req = new ListReq();
		req.setUid(1243L);
		//req.setType(1);
		//req.setSdate("2018-03-28");
		//req.setBdate("2018-03-31");
		
		ListResult renterList = renterInfoService.renterList(req);
		String string = JSON.toJSONString(renterList);
		System.out.println(string);
	}
	
	
	
	public static void tesRenterDetail(ApplicationContext ctx){
		RenterInfoService renterInfoService = (RenterInfoService)ctx.getBean("renterInfoService");
		DetailReq req = new DetailReq();
		req.setRenterId(1000L);
		req.setUid(1243L);
		DetailResult renter = renterInfoService.getRenter(req);
		String string = JSON.toJSONString(renter);
		System.out.println(string);
	}
	
	public static void tesRenterConfigSave(ApplicationContext ctx){
		RenterInfoService renterInfoService = (RenterInfoService)ctx.getBean("renterInfoService");
		SaveConfigReq req = new SaveConfigReq();
		req.setChargeMode(2);
		req.setId(1001L);
		req.setRenterId(1000L);
		req.setStartTime("2018-03-31");
		req.setUid(1243L);
		List<RenterMeterInfo> meterList = new ArrayList<>();
		RenterMeterInfo meter1 = new RenterMeterInfo();
		meter1.setBuildingId(1024L);
		meter1.setId(1069L);
		meter1.setRate(44.0D);
		RenterMeterInfo meter2 = new RenterMeterInfo();
		meter2.setBuildingId(1024L);
		meter2.setId(1070L);
		meter2.setRate(40.0D);
		RenterMeterInfo meter3 = new RenterMeterInfo();
		meter3.setBuildingId(1027L);
		meter3.setId(1068L);
		meter3.setRate(40.0D);
		meterList.add(meter1);
		meterList.add(meter2);
		meterList.add(meter3);
		req.setMeterList(meterList );
		
		SaveResult result = renterInfoService.saveRenterConfig(req);
		String string = JSON.toJSONString(result);
		System.out.println(string);
	}
	
}
