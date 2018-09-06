package com.edianniu.pscp.search;

import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.search.dubbo.ReportProduceDubboService;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.DayDetailLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.list.DayDetailLogListReqData;
import com.edianniu.pscp.search.support.meter.vo.DayDetailLogVO;
import com.edianniu.pscp.search.util.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DayDetailLogTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");
	private ReportProduceDubboService reportProduceDubboService=null;
	private ReportSearchDubboService reportSearchDubboService=null;
	@Before
	public void init(){
		 reportProduceDubboService=(ReportProduceDubboService) ctx.getBean("reportProduceDubboService");
		 reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
	}
	@Test
	public void produce() {
		
		int day=20180408;
		Double charge=0.00;
		Double rate=1D;//根据电表读出
		for(int i=0;i<1;i++){
			DayDetailLogReqData reqData=new DayDetailLogReqData();
			
			reqData.setCompanyId(102L);
			reqData.setMeterId("#000002");
			reqData.setCompanyName("测试客户101");
			reqData.setCreateTime(new Date().getTime());
			reqData.setDate((day+i)+"");
			reqData.setPeriod(15);
			Date startTime=DateUtils.parse((day+i)+" 00:00:00",DateUtils.DATE_TIME_PATTERN2);
			for(int j=0;j<(24*60/15);j++){
				Date endTime=DateUtils.addMinutes(startTime, reqData.getPeriod());
				reqData.setStartTime(startTime.getTime());
				reqData.setEndTime(endTime.getTime());
				startTime=endTime;
				Double start=charge;
				Double end=start+new Random(10).nextDouble();
				reqData.setStart(start);
				reqData.setEnd(end);
				charge=end;
				reqData.setRate(1);
				reqData.setElectric(end-start);
				reqData.setChargeType(1);//根据startTime和endTime计算
				reqData.setPrice(0.5);//根据chargeType计算写入单价
				reqData.setFee(reqData.getElectric()*reqData.getRate()*reqData.getPrice());
				reqData.setFactor(0.97D);
				OpResult result=reportProduceDubboService.save(reqData);
				System.out.println(result.getResultCode()+":"+result.getResultMessage());
			}
			
		}
		
	}
	//@Test
	public void search() {
		DayDetailLogListReqData reqData=new DayDetailLogListReqData();
		reqData.setCompanyId(1121L);
		//reqData.setMeterId("#000002");
		reqData.setDate("20180125");
		reqData.setStartTime(1516863600000L);
		reqData.setEndTime(1516864500000L+60000L);
		Integer[] meterTypes={2,3};
		reqData.setMeterTypes(meterTypes);
		reqData.setOffset(0);
		reqData.setPageSize(100);
		ReportListResult<DayDetailLogVO> result=reportSearchDubboService.search(reqData);
		System.out.println("searchResult:"+JSON.toJSONString(result));
	}
	
	

}
