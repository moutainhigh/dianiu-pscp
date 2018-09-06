package com.edianniu.pscp.search;

import java.util.Date;

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
import com.edianniu.pscp.search.support.SortOrder;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.list.DemandDetailListReqData;
import com.edianniu.pscp.search.support.meter.vo.DemandDetailVO;
import com.edianniu.pscp.search.util.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemandDetailTests {
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
		String[] times={"0015","0030","0045","0100","0115"};
		for(String time:times){
			DemandDetailReqData reqData=new DemandDetailReqData();
			reqData.setCompanyId(999L);
			reqData.setMeterId("#000003");
			reqData.setCompanyName("测试客户101");
			reqData.setDate("20180408"+time);
			reqData.setPower(50+Math.random());
			reqData.setCreateTime(new Date().getTime());
			OpResult result=reportProduceDubboService.save(reqData);
			System.out.println(result.getResultCode()+":"+result.getResultMessage());
		}
		
		
	}
	@Test
	public void search() {
		DemandDetailListReqData reqData=new DemandDetailListReqData();
		//reqData.setFcompanyId(1L);
		reqData.setCompanyId(1112L);
		reqData.setMeterId("E20180110");
		//reqData.setDate("201712");
		reqData.setOffset(0);
		reqData.setPageSize(200);
		reqData.addSort("power", SortOrder.DESC);
		ReportListResult<DemandDetailVO> result=reportSearchDubboService.search(reqData);
		/*for(DemandDetailVO vo:result.getList()){
			DemandDetailReqData reqData1=new DemandDetailReqData();
			reqData1.setCompanyId(vo.getCompanyId());
			reqData1.setMeterId(vo.getMeterId());
			reqData1.setCompanyName("陈炎林物业管理有限公司");
			reqData1.setDate(vo.getDate());
			reqData1.setTime(DateUtils.parse(vo.getDate(), "yyyyMMddHHmm").getTime());
			reqData1.setPower(vo.getPower());
			reqData1.setCreateTime(vo.getCreateTime());
			reqData1.setUpdateTime(new Date().getTime());
			reqData1.setSubTermCode("01A00");
			reqData1.setType(1);
			reqData1.setValue(15);
			OpResult result1=reportProduceDubboService.save(reqData1);
			System.out.println(result1.getResultCode()+":"+result1.getResultMessage());
		}*/
		System.out.println("searchResult:"+JSON.toJSONString(result));
		DemandDetailReqData reqData1=new DemandDetailReqData();
		reqData1.setCompanyId(102L);
		reqData1.setMeterId("#000003");
		reqData1.setDate("20171229 00:15");
		DemandDetailVO demandDetailVO=reportSearchDubboService.getById(reqData1);
		System.out.println("demandDetailVO:"+JSON.toJSONString(demandDetailVO));
		Double maxPower=reportSearchDubboService.getMaxPower(1112L, "E20180110", DateUtils.parse("201712030115", "yyyyMMddHHmm").getTime(), DateUtils.parse("201712030215", "yyyyMMddHHmm").getTime());
		System.out.println("maxPower:"+maxPower);
	}
	
	

}
