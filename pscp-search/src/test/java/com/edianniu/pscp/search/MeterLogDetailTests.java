package com.edianniu.pscp.search;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.edianniu.pscp.search.dubbo.ReportProduceDubboService;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.list.DistinctMeterLogListReqData;
import com.edianniu.pscp.search.support.meter.vo.MeterLogVO;
import com.edianniu.pscp.search.support.meter.vo.StatLog;
import com.edianniu.pscp.search.util.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeterLogDetailTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");
	//@Test
	public void produce() {
		ReportProduceDubboService reportProduceDubboService=(ReportProduceDubboService) ctx.getBean("reportProduceDubboService");
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		
		
		for(int i=0;i<3;i++){
			MeterLogReqData reqData=new MeterLogReqData();
			
			reqData.setCompanyId(997L);
			reqData.setMeterId("#000007");
			reqData.setCompanyName("测试客户7_"+i);
			reqData.setPositiveApexActivePowerCharge(100.00+i);
			reqData.setPositivePeakPowerCharge(100.00+i);
			reqData.setPositiveFlatPowerCharge(100.00+i);
			reqData.setPositiveValleyPowerCharge(100.00+i);
			reqData.setPositiveTotalActivePowerCharge(
					reqData.getPositiveApexActivePowerCharge()+
					reqData.getPositivePeakPowerCharge()+
					reqData.getPositiveFlatPowerCharge()+
					reqData.getPositiveValleyPowerCharge());
			reqData.setUa(200.21+i);
			reqData.setUb(200.22+i);
			reqData.setUc(200.20+i);
			reqData.setuStatus(1);
			reqData.setUaStatus(0);
			reqData.setUbStatus(0);
			reqData.setUcStatus(0);
			reqData.setIa(12.92+i);
			reqData.setIb(12.90+i);
			reqData.setIc(12.91+i);
			reqData.setiUnbalanceDegree(0.20);
			reqData.setIaUnbalanceDegree(0.21);
			reqData.setIbUnbalanceDegree(0.19);
			reqData.setIcUnbalanceDegree(0.15);
			reqData.setaActivePower(12312.221);
			reqData.setTime(new Date().getTime());
			MeterLogVO vo=reportSearchDubboService.getById(reqData);
			if(vo!=null){
				reqData.setCreateTime(vo.getCreateTime());
				reqData.setUpdateTime(new Date().getTime());
			}
			else{
				reqData.setCreateTime(new Date().getTime());
			}
			
			OpResult result=reportProduceDubboService.save(reqData);
			System.out.println(result.getResultCode()+":"+result.getResultMessage());
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//@Test
	public void search() {
			ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
			DistinctMeterLogListReqData reqData=new DistinctMeterLogListReqData();
			reqData.setCompanyId(102L);
			reqData.setPageSize(100);
			//ReportListResult<MeterLogVO> result=reportSearchDubboService.searchDistinctByMeterId(reqData);
			//System.out.println("searchResult:"+JSON.toJSONString(result));
			//reportSearchDubboService.getAvgPower(102L, "#000002", 1514348132186L,1514348133297L);
			Double rate=reportSearchDubboService.getVoltageQualifiedRate(102L, "", null, null);
			System.out.println("rate:"+rate);
	}
	//@Test
	public void searchGroup() {
		    //根据日期分组 按月/按日/按年分组，分组里 求 电量最大值|最小值 (最大-最小就是总电量)，电压最大值，电流最大值，功率最大值
			ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
			Long startTime=DateUtils.parse("2018-03-01 00:00:00", DateUtils.DATE_TIME_PATTERN).getTime();
			Long endTime=DateUtils.parse("2018-03-31 23:59:59",DateUtils.DATE_TIME_PATTERN).getTime();
			Double rate=reportSearchDubboService.getVoltageQualifiedRate(1131L, "030100A00201001", startTime, endTime);
			System.out.println("rate:"+rate);
	}
	@Test
	public void searchWeek() {
	    //根据日期分组 按月/按日/按年分组，分组里 求 电量最大值|最小值 (最大-最小就是总电量)，电压最大值，电流最大值，功率最大值
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		Set<String> meterIds=new HashSet<String>();
		meterIds.add("330100A00201002");
		meterIds.add("330100A00201001");
		meterIds.add("330100A00201003");
		String type="DAY";//DAY MONTH YEAR
		List<StatLog> list=reportSearchDubboService.getStatLogs("20180501", "20180524",type, meterIds, 1144L);
		System.out.println("list:"+list);
}
	

}
