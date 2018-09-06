package com.edianniu.pscp.search;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.search.dubbo.ReportProduceDubboService;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.list.DayAggregateListReqData;
import com.edianniu.pscp.search.support.meter.vo.DayAggregateVO;
import com.edianniu.pscp.search.support.meter.vo.MonthElectricChargeVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonthLogTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");
	
	@Test
	public void produce() {
		ReportProduceDubboService reportProduceDubboService=(ReportProduceDubboService) ctx.getBean("reportProduceDubboService");
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		int day=201801;
		for(int i=0;i<12;i++){
			MonthLogReqData reqData=new MonthLogReqData();
			reqData.setCompanyId(998L);
			reqData.setMeterId("#test000001");
			
			reqData.setDate((day+i)+"");
			reqData.setCompanyName("测试客户101");
			reqData.setCycle("20171017~20171117");
			reqData.setTotal(300D);
			reqData.setTotalCharge(100.32D);
			reqData.setBasicCharge(2000.04D);
			reqData.setApexCharge(100.32D);
			reqData.setFlatCharge(100.32D);
			reqData.setPeakCharge(100.32D);
			reqData.setValleyCharge(100.32D);
			reqData.setFactorCharge(100.32D);
			reqData.setApexPrice(100.32D);
			reqData.setBasicPrice(100.32D);
			reqData.setActivePowerFactor(0.94D);
			reqData.setFlatPrice(100.32D);
			reqData.setPeakPrice(100.32D);
			reqData.setValleyPrice(100.32D);
			MonthElectricChargeVO monthLogVO=reportSearchDubboService.getById(reqData);
			if(monthLogVO!=null){
				reqData.setCreateTime(monthLogVO.getCreateTime());
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
	public void search() {
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		DayAggregateListReqData reqData=new DayAggregateListReqData();
		//reqData.setFcompanyId(1L);
		//reqData.setCcompanyId(101L);
		reqData.setMeterId("#000001");
		reqData.setOffset(0);
		reqData.setPageSize(30);
		ReportListResult<DayAggregateVO> result=reportSearchDubboService.search(reqData);
		System.out.println("searchResult:"+JSON.toJSONString(result));
	}
	
	

}
