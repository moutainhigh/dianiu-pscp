package com.edianniu.pscp.search;

import java.util.Date;
import java.util.Random;

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
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.list.DayLoadDetailListReqData;
import com.edianniu.pscp.search.support.meter.vo.DayLoadDetailVO;
import com.edianniu.pscp.search.util.DateUtils;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DayLoadDetailTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");
	//@Test
	public void produce() {
		ReportProduceDubboService reportProduceDubboService=(ReportProduceDubboService) ctx.getBean("reportProduceDubboService");
		ReportSearchDubboService reportSearchDubboService=(ReportSearchDubboService) ctx.getBean("reportSearchDubboService");
		int day=20171201;
		Date time=DateUtils.parse(day+" 00:00:00", DateUtils.DATE_TIME_PATTERN2);
		for(int i=0;i<(24*60/15);i++){
			DayLoadDetailReqData reqData=new DayLoadDetailReqData();
			
			reqData.setCompanyId(102L);
			reqData.setMeterId("#000005");
			
			reqData.setDate(day+"");
			reqData.setCompanyName("测试客户101");
			Random rd=new Random(1);
			reqData.setLoad(20+rd.nextDouble());
			reqData.setPeriod("15");
			reqData.setTime(DateUtils.format(time, DateUtils.TIME_PATTERN));
			time=DateUtils.addMinutes(time, 15);
			DayLoadDetailVO dayLoadDetailVO=reportSearchDubboService.getById(reqData);
			if(dayLoadDetailVO!=null){
				reqData.setCreateTime(dayLoadDetailVO.getCreateTime());
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
			DayLoadDetailListReqData reqData=new DayLoadDetailListReqData();
			//reqData.setFcompanyId(1L);
			reqData.setCompanyId(1112L);
			reqData.addSort("time", SortOrder.DESC);
			reqData.setMeterId("E20180110");
			reqData.setDate("20171203");
			reqData.setOffset(0);
			reqData.setPageSize(200);
			ReportListResult<DayLoadDetailVO> result=reportSearchDubboService.search(reqData);
			/*Optional<DayLoadDetailVO> optional=result.getList().stream().max(Comparator.comparing(DayLoadDetailVO::getLoad));
			DayLoadDetailVO dayLoad=optional.get();*/
			System.out.println("searchResult:"+JSON.toJSONString(result));
	}
	

}
