package com.edianniu.pscp.search;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.search.dubbo.ReportProduceDubboService;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.AvgListResult;
import com.edianniu.pscp.search.support.meter.AvgOfMetreReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.StatListResult;
import com.edianniu.pscp.search.support.meter.list.DayAggregateListReqData;
import com.edianniu.pscp.search.support.meter.list.DayElectricListReqData;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.AvgOfMeterStat;
import com.edianniu.pscp.search.support.meter.vo.DayAggregateVO;
import com.edianniu.pscp.search.support.meter.vo.DayElectricVO;
import com.edianniu.pscp.search.support.meter.vo.DayLogVO;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DayLogTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");
	
	@Test
	public void produceDayElectricCharge() {//日用电以及电费数据
		ReportProduceDubboService reportProduceDubboService=(ReportProduceDubboService) ctx.getBean("reportProduceDubboService");
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		int day=20180501;
		for(int i=1;i<31;i++){
			DayLogReqData reqData=new DayLogReqData();
			reqData.setCompanyId(773L);
			reqData.setMeterId("773TEST001");
			reqData.setDate((day+i)+"");
			reqData.setCompanyName("773TEST客户");
			reqData.setTotalCharge(100.22);
			reqData.setApexCharge(20.10);
			reqData.setPeakCharge(23.09);
			reqData.setFlatCharge(27.02);
			reqData.setValleyCharge(100.00);
			reqData.setTotal(1800.0);
			reqData.setApex(600.0);
			reqData.setPeak(300.00);
			reqData.setFlat(400.00);
			reqData.setValley(500.00);
			reqData.setApexPrice(50.10);
			reqData.setPeakPrice(23.09);
			reqData.setFlatPrice(27.02);
			reqData.setValleyPrice(100.00);
			DayLogVO vo=reportSearchDubboService.getById(reqData);
			if(vo!=null){
				reqData.setCreateTime(vo.getCreateTime());
				reqData.setUpdateTime(new Date().getTime());
			}
			else{
				reqData.setCreateTime(new Date().getTime());
			}
			
			OpResult result=reportProduceDubboService.save(reqData);
			System.out.println(result.getResultCode()+":"+result.getResultMessage());
		}
		
	}
	//@Test
	public void searchDayLog() {
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		/*DayElectricChargeListReqData reqData=new DayElectricChargeListReqData();
	
		reqData.setCompanyId(102L);
		reqData.setMeterId("#000002");
		reqData.setDate("20171201");
		reqData.setOffset(0);
		reqData.setPageSize(30);
		ReportListResult<DayElectricChargeVO> result=reportSearchDubboService.search(reqData);
		System.out.println("searchResult:"+JSON.toJSONString(result));*/
		long start=System.currentTimeMillis();
		ElectricChargeStatReqData dayStatReqData=new ElectricChargeStatReqData();
		dayStatReqData.setCompanyId(1131L);
		dayStatReqData.setFromDate("20180401");
		dayStatReqData.setToDate("20180413");

		dayStatReqData.setType("DAY");

		dayStatReqData.setSource("DAY_LOG");
		Set<String> meterIds=new HashSet<String>();
		meterIds.add("285100A00101009");
		meterIds.add("285100A00101006");
		meterIds.add("285100A00101005");
		/*meterIds.add("030100A00201052");
		meterIds.add("030100A00201051");
		meterIds.add("030100A00201003");*/
		dayStatReqData.setMeterIds(meterIds);
		StatListResult<ElectricChargeStat> dayStats=reportSearchDubboService.getElectricChargeStats(dayStatReqData);
		System.out.println("日电费数据："+JSON.toJSON(dayStats)+",执行时间:"+(System.currentTimeMillis()-start)+" ms");
		/*ElectricChargeStatReqData monthStatReqData=new ElectricChargeStatReqData();
		monthStatReqData.setCompanyId(1131L);
		monthStatReqData.setFromDate("201701");
		monthStatReqData.setToDate("201805");
		monthStatReqData.setType("MONTH");
		monthStatReqData.setMeterIds(meterIds);
		monthStatReqData.setSource("MONTH_LOG");
		StatListResult<ElectricChargeStat> monthStats=reportSearchDubboService.getElectricChargeStats(monthStatReqData);
		System.out.println("月电费数据："+JSON.toJSON(monthStats));
		
		ElectricChargeStatReqData yearStatReqData=new ElectricChargeStatReqData();
		yearStatReqData.setCompanyId(1131L);
		yearStatReqData.setFromDate("201701");
		yearStatReqData.setToDate("201812");
		yearStatReqData.setType("YEAR");
		yearStatReqData.setMeterIds(meterIds);
		yearStatReqData.setSource("MONTH_LOG");
		StatListResult<ElectricChargeStat> yearStats=reportSearchDubboService.getElectricChargeStats(yearStatReqData);
		System.out.println("年电费数据："+JSON.toJSON(yearStats));*/
		
	}
	//@Test
	public void search() {
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		DayElectricListReqData reqData=new DayElectricListReqData();
		
		reqData.setCompanyId(1112L);
		reqData.setMeterId("E20180110");
		reqData.setDate("201801");
		reqData.setOffset(0);
		reqData.setPageSize(31);
		ReportListResult<DayElectricVO> result=reportSearchDubboService.search(reqData);
		System.out.println("searchResult:"+JSON.toJSONString(result));
	}
	@Test
	public void search1() {
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		ElectricChargeStatReqData dayStatReqData=new ElectricChargeStatReqData();
		dayStatReqData.setCompanyId(1138L);
		dayStatReqData.setFromDate("201804");
		dayStatReqData.setToDate("201804");
		dayStatReqData.setType("MONTH");
		dayStatReqData.setSource("MONTH_LOG");
		Set<String> meterIds=new HashSet<String>();
		meterIds.add("285100A00101009");
		meterIds.add("285100A00101006");
		meterIds.add("285100A00101005");
		/*meterIds.add("030100A00201052");
		meterIds.add("030100A00201051");
		meterIds.add("030100A00201003");*/
		dayStatReqData.setMeterIds(meterIds);
		StatListResult<ElectricChargeStat> dayStats=reportSearchDubboService.getElectricChargeStats(dayStatReqData);
		System.out.println("日电费数据："+JSON.toJSON(dayStats));
	}
	
	@Test
	public void avgOfMeter(){
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		AvgOfMetreReqData req = new AvgOfMetreReqData();
		req.setCompanyId(773L);
		req.setFromDate("20180401");
		Set<String> meterIds=new HashSet<String>();
		meterIds.add("773TEST001");
		req.setMeterIds(meterIds);
		
		AvgListResult<AvgOfMeterStat> result = reportSearchDubboService.avgOfMeter(req);
		System.out.println(JSON.toJSONString(result));
	}

}
