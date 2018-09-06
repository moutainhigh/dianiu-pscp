package test.message.meterlog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.GateWayStatus;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.service.dubbo.MeterLogInfoService;
import com.edianniu.pscp.message.web.util.DateUtils;

public class MeterLogTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");

	private Map<String, Object> getMeterDate(Date startTime, Date reportTime) {

		Map<String, Object> meterData = new HashMap<String, Object>();
		meterData.put("time", reportTime.getTime());
		// 遥测数据
		Random powerR = new Random(300);
		meterData.put("abcActivePower", 3100D + powerR.nextInt(90));// 三相总有功功率
		meterData.put("aActivePower", 1000D + powerR.nextInt(30));// A相有功功率
		meterData.put("bActivePower", 1000D + powerR.nextInt(40));// B相有功功率
		meterData.put("cActivePower", 1000D + powerR.nextInt(50));// C相有功功率
		meterData.put("abcReactivePower", 10D);// 三相总无功功率
		meterData.put("aReactivePower", 10D);// A相无功功率
		meterData.put("bReactivePower", 10D);// B相无功功率
		meterData.put("cReactivePower", 10D);// C相无功功率
		meterData.put("abcApparentPower", 1020D);// 三相总视在功率
		meterData.put("aApparentPower", 1020D);// A相视在功率
		meterData.put("bApparentPower", 1020D);// B相视在功率
		meterData.put("cApparentPower", 1020D);// C相视在功率

		meterData.put("abcPowerFactor", 0.98D);// 三相总功率因数
		meterData.put("aPowerFactor", 0.98D);// A相功率因数
		meterData.put("bPowerFactor", 0.98D);// B相功率因数
		meterData.put("cPowerFactor", 0.98D);// C相功率因数
		meterData.put("positiveActivePowerMaxDemand", 1000.2D);// 正向有功最大需量
		meterData.put("negativeActivePowerMaxDemand", 1000.2D);// 反向有功最大需量
		meterData.put("positiveReactivePowerMaxDemand", 1000.2D);// 正向无功最大需量
		meterData.put("negativeReactivePowerMaxDemand", 1000.2D);// 反向无功最大需量
		Random uR = new Random(50);
		meterData.put("ua", 220D + uR.nextInt(50));// A相电压
		meterData.put("ub", 220D + uR.nextInt(70));// B相电压
		meterData.put("uc", 220D + uR.nextInt(60));// C相电压
		Random iR = new Random(30);
		meterData.put("ia", 111.1D + iR.nextInt(90));// A相电流
		meterData.put("ib", 114.1D + iR.nextInt(20));// B相电流
		meterData.put("ic", 117.1D + iR.nextInt(100));// C相电流

		// 电度数据
		Double ptapc = (reportTime.getTime() - startTime.getTime()) / 1000D;
		meterData.put("positiveTotalActivePowerCharge", ptapc);// 正向总有功电度
		meterData.put("positiveApexActivePowerCharge", ptapc * 0.2);// 正向尖有功电度
		meterData.put("positivePeakPowerCharge", ptapc * 0.3);// 正向峰有功电度
		meterData.put("positiveFlatPowerCharge", ptapc * 0.22);// 正向平有功电度
		meterData.put("positiveValleyPowerCharge", ptapc * 0.28);// 正向谷有功电度
		Double ntapc = (reportTime.getTime() - startTime.getTime()) / 7D;
		meterData.put("negativeTotalActivePowerCharge", ntapc);// 反向总有功电度
		meterData.put("negativeApexActivePowerCharge", ntapc * 0.2);// 反向尖有功电度
		meterData.put("negativePeakPowerCharge", ntapc * 0.3);// 反向峰有功电度
		meterData.put("negativeFlatPowerCharge", ntapc * 0.23);// 反向平有功电度
		meterData.put("negativeValleyPowerCharge", ntapc * 0.27);// 反向谷有功电度
		Double ptrpc = (reportTime.getTime() - startTime.getTime()) / 13D;
		meterData.put("positiveTotalReactivePowerCharge", ptrpc);// 正向总无功电度
		meterData.put("positiveApexReactivePowerCharge", ptrpc * 0.3);// 正向尖无功电度
		meterData.put("positivePeakReactivePowerCharge", ptrpc * 0.2);// 正向峰无功电度
		meterData.put("positiveFlatReactivePowerCharge", ptrpc * 0.24);// 正向平无功电度
		meterData.put("positiveValleyReactivePowerCharge", ptrpc * 0.26);// 正向谷无功电度
		Double ntrpc = (reportTime.getTime() - startTime.getTime()) / 13D;
		meterData.put("negativeTotalReactivePowerCharge", ntrpc);// 反向总无功电度
		meterData.put("negativeApexReactivePowerCharge", ntrpc * 0.24);// 反向尖无功电度
		meterData.put("negativePeakReactivePowerCharge", ntrpc * 0.25);// 反向峰无功电度
		meterData.put("negativeFlatReactivePowerCharge", ntrpc * 0.27);// 反向平无功电度
		meterData.put("negativeValleyReactivePowerCharge", ntrpc * 0.24);// 反向谷无功电度
		return meterData;
	}

	// 1号楼总表
	@Test
	public void createMeterLogE2018011001() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E2018011001");

			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01A00");
			Date startTime = DateUtils.parse("2018-01-05 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 1号楼总表-照明
	//@Test
	public void createMeterLogE201801100101() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100101");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01A00");// 01A00 照明，01B00 空调,01C00 动力，01E00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 1号楼总表-动力
	//@Test
	public void createMeterLogE201801100102() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100102");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01C00");// 01A00 照明，01B00 空调,01C00 动力，01E00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 1号楼总表-空调
	//@Test
	public void createMeterLogE201801100103() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100103");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01B00");// 01A00 照明，01B00 空调,01C00 动力，01E00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 1号楼总表-特殊
	//@Test
	public void createMeterLogE201801100104() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100104");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01D00");// 01A00 照明，01B00 空调,01C00 动力，01D00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 2号楼总表
	@Test
	public void createMeterLogE2018011002() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E2018011002");

			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01A00");
			Date startTime = DateUtils.parse("2018-01-05 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 2号楼总表-照明
	//@Test
	public void createMeterLogE201801100105() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100105");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01A00");// 01A00 照明，01B00 空调,01C00 动力，01E00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 2号楼总表-动力
	//@Test
	public void createMeterLogE201801100106() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100106");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01C00");// 01A00 照明，01B00 空调,01C00 动力，01E00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 1号楼总表-空调
	//@Test
	public void createMeterLogE201801100107() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100107");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01B00");// 01A00 照明，01B00 空调,01C00 动力，01E00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 1号楼总表-特殊
	//@Test
	public void createMeterLogE201801100108() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E201801100108");
			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01D00");// 01A00 照明，01B00 空调,01C00 动力，01D00
												// 特殊，01X00，其他
			Date startTime = DateUtils.parse("2018-01-12 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	// 总表
	@Test
	public void createMeterLogE20180110() {
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx
				.getBean("meterLogInfoService");
		Date reportTime = DateUtils.parse("2018-01-15 14:30:00",
				"yyyy-MM-dd HH:mm:ss");
		Date reportEndTime = DateUtils.parse("2018-01-16 15:30:00",
				"yyyy-MM-dd HH:mm:ss");
		for (int i = 0; reportTime.getTime() < reportEndTime.getTime(); i++) {
			MeterLogInfo meterLog = new MeterLogInfo();
			meterLog.setMeterId("E20180110");

			meterLog.setReportTime(reportTime);
			meterLog.setSubTermCode("01A00");
			Date startTime = DateUtils.parse("2018-01-01 00:00:00");
			meterLog.setData(this.getMeterDate(startTime, reportTime));
			List<MeterLogInfo> meterLogs=new ArrayList<MeterLogInfo>();
			meterLogs.add(meterLog);
			Result result = meterLogInfoService.pushMeterLog(meterLogs);
			System.out.println("result:" + result);
			System.out.println("meterLog:" + JSON.toJSONString(meterLog));
			reportTime = DateUtils.parse(DateUtils.getDateOffsetMinute(
					reportTime, 1, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
		}
	}
	
	// 测试设备上线下线
	@Test
	public void testOnOffLine(){
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx.getBean("meterLogInfoService");
		GateWayInfo gateWayInfo = new GateWayInfo();
		gateWayInfo.setType(1);
		gateWayInfo.setStatus(GateWayStatus.ONLINE.getValue());
		gateWayInfo.setGatewayId("01");
		gateWayInfo.setBuildingId("050001Z001");
		gateWayInfo.setReportTime(new Date());
		Map<String,Object> gateWayData=new HashMap<String,Object>();
		gateWayData.put("dev_num", "10");
		gateWayData.put("build_name", "测试楼");
		gateWayData.put("begin_time", "201803071200");
		gateWayData.put("build_no", "050001Z001");
		gateWayData.put("dev_no", "5");
		gateWayInfo.setGateWayData(gateWayData);
		
		Result result = meterLogInfoService.pushGateWay(gateWayInfo);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
	}
	
	// 测试添加仪表
	@Test
	public void testPushMeter(){
		MeterLogInfoService meterLogInfoService = (MeterLogInfoService) ctx.getBean("meterLogInfoService");
		List<MeterInfo> meters = new ArrayList<>();
		MeterInfo meterInfo = new MeterInfo();
		meterInfo.setBuildingId("050001Z001");//050001Z00101002
		meterInfo.setGateWayId("01");
		meterInfo.setMeterId("002");
		meterInfo.setSubTermCode("01C00");
		meterInfo.setType(1);
		meterInfo.setFunctions("[{'attr':{'coding':'01A00','id':'01'}},{'attr':{'coding':'01A00','id':'02'}}]");
		meterInfo.setReportTime(new Date());
		Map<String, Object> meterData = new HashMap<>();
		meterData.put("address", "ceshi");
		meterData.put("name", "name");
		meterInfo.setMeterData(meterData);
		meters.add(meterInfo);
		Result result = meterLogInfoService.pushMeter(meters);
		String jsonString = JSON.toJSONString(result);
		System.out.println(jsonString);
	}

}
