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
import com.edianniu.pscp.search.support.SortOrder;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.list.DayVoltageCurrentDetailListReqData;
import com.edianniu.pscp.search.support.meter.vo.DayVoltageCurrentDetailVO;
import com.edianniu.pscp.search.util.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DayVoltageCurrentDetailTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");
	//@Test
	public void produce() {
		ReportProduceDubboService reportProduceDubboService=(ReportProduceDubboService) ctx.getBean("reportProduceDubboService");
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		int day=20180408;
		Date time=DateUtils.parse(day+" 00:00:00", DateUtils.DATE_TIME_PATTERN2);
		for(int i=0;i<60;i++){
			DayVoltageCurrentDetailReqData reqData=new DayVoltageCurrentDetailReqData();
			
			reqData.setCompanyId(102L);
			reqData.setMeterId("#000010");
			
			reqData.setDate(day+"");
			reqData.setCompanyName("测试客户101");
			reqData.setUa(200.1D);
			reqData.setUb(201.1D);
			reqData.setUc(202.2D);
			reqData.setuStatus(1);
			reqData.setUaStatus(1);
			reqData.setUbStatus(0);
			reqData.setUcStatus(0);
			reqData.setIa(110.1);
			reqData.setIb(110.2);
			reqData.setIc(110.3);
			reqData.setiUnbalanceDegree(0.01);
			reqData.setIaUnbalanceDegree(0.02);
			reqData.setIbUnbalanceDegree(0.03);
			reqData.setIcUnbalanceDegree(0.04);
			reqData.setPeriod(15);
			reqData.setTime(DateUtils.format(time, DateUtils.TIME_PATTERN));
			time=DateUtils.addMinutes(time, 15);
			DayVoltageCurrentDetailVO dayVoltageCurrentVO=reportSearchDubboService.getById(reqData);
			if(dayVoltageCurrentVO!=null){
				reqData.setCreateTime(dayVoltageCurrentVO.getCreateTime());
				reqData.setUpdateTime(new Date().getTime());
			}
			else{
				reqData.setCreateTime(new Date().getTime());
			}
			
			OpResult result=reportProduceDubboService.save(reqData);
			System.out.println(result.getResultCode()+":"+result.getResultMessage());
		}
		
	}
	@Test
	public void search() {
			ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
			DayVoltageCurrentDetailListReqData reqData=new DayVoltageCurrentDetailListReqData();
			reqData.setCompanyId(1127L);
			reqData.addSort("time", SortOrder.DESC);
			reqData.setMeterId("030100A00001001");
			reqData.setDate("20180201");
			reqData.setPeriod(15);
			reqData.setOffset(0);
			reqData.setPageSize(200);
			ReportListResult<DayVoltageCurrentDetailVO> result=reportSearchDubboService.search(reqData);
			System.out.println("searchResult:"+JSON.toJSONString(result));
	}
	

}
