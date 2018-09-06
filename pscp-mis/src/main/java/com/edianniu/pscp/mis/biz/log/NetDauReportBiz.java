package com.edianniu.pscp.mis.biz.log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;










import stc.skymobi.cache.redis.JedisUtil;
/*import stc.skymobi.common.AcceptInfo;
 import stc.skymobi.common.Configuration;
 import stc.skymobi.common.ConnectInfo;
 import stc.skymobi.common.ServerInfo;*/
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
import stc.skymobi.netty.transport.Sender;
import stc.skymobi.netty.transport.TransportUtils;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.edianniu.pscp.message.bean.MeterLogInfo;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.service.dubbo.MeterLogInfoService;
import com.edianniu.pscp.mis.bean.log.Attribute;
import com.edianniu.pscp.mis.bean.log.CommonInfo;
import com.edianniu.pscp.mis.bean.log.FunctionInfo;
import com.edianniu.pscp.mis.bean.log.MeterInfo;
import com.edianniu.pscp.mis.bean.log.ReportConfig;
import com.edianniu.pscp.mis.bean.log.ReportInfo;
import com.edianniu.pscp.mis.bean.request.log.NetDauQueryRequest;
import com.edianniu.pscp.mis.bean.request.log.NetDauReportRequest;
import com.edianniu.pscp.mis.bean.response.log.NetDauQueryResponse;
import com.edianniu.pscp.mis.bean.response.log.NetDauReportResponse;
import com.edianniu.pscp.mis.biz.NetDauBiz;
import com.edianniu.pscp.mis.log.DWLogger;
import com.edianniu.pscp.mis.util.DateUtils;
import com.edianniu.pscp.mis.util.Md5Utils;

/**
 * netdau采集器report数据
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:10:05
 * @version V1.0
 */
public class NetDauReportBiz extends
		NetDauBiz<NetDauReportRequest, NetDauReportResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(NetDauReportBiz.class);
	@Autowired
	private MeterLogInfoService meterLogInfoService;
	@Autowired
	private JedisUtil jedisUtil;
	private String logWriteType;
    private static final  DWLogger dwLogger=new DWLogger();
	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauReportRequest req) {
			logger.debug("NetDauReportRequest recv req : \r\n{}", req);
			NetDauReportResponse resp = (NetDauReportResponse) initResponse(
					ctx, req, new NetDauReportResponse());
			String reportAck = "fail";
			try{
				if (req.getCommon().getType().equals("continuous")
						|| req.getCommon().getType().equals("report")||req.getCommon().getType().equals("reply")) {
					ReportInfo reportInfo = req.getData();
					Date reportTime = DateUtils.parse(reportInfo.getTime(),
							"yyyyMMddHHmmss");
					List<MeterLogInfo> meterLogs = new ArrayList<MeterLogInfo>();
					List<com.edianniu.pscp.message.bean.MeterInfo> meters = new ArrayList<com.edianniu.pscp.message.bean.MeterInfo>();
					List<MeterInfo> list = reportInfo.getMeter();
					for (MeterInfo meterInfo : list) {
						List<FunctionInfo> funcionInfos = meterInfo.getFunction();
						// 构建仪表信息,存储仪表的信息以及仪表采集的参数信息(最新信息)
						com.edianniu.pscp.message.bean.MeterInfo meter = new com.edianniu.pscp.message.bean.MeterInfo();
						meter.setBuildingId(req.getCommon().getBuilding_id());
						meter.setType(1);
						meter.setGateWayId(req.getCommon().getGateway_id());
						meter.setMeterId(meterInfo.getAttr().getId());
						meter.getMeterData().put("id", meterInfo.getAttr().getId());
						meter.getMeterData().put("address",
								meterInfo.getAttr().getAddr());
						meter.getMeterData().put("type",
								meterInfo.getAttr().getTp());
						meter.getMeterData().put("name",
								meterInfo.getAttr().getName());
						meter.setReportTime(reportTime);
						
						//构建仪表日志信息
						MeterLogInfo meterLog = new MeterLogInfo();
						meterLog.setReportTime(reportTime);
						//仪表ID等于建筑编号+网关编号+仪表编号
						String meterId = req.getCommon().getBuilding_id()
								+ req.getCommon().getGateway_id()
								+ meterInfo.getAttr().getId();
						meterLog.setMeterId(meterId);
						// time
						meterLog.getData().put("time", reportTime.getTime());
						String subTermCode = "";
						for (FunctionInfo functionInfo : funcionInfos) {// 这块可以针对不同的仪表做一些配置,目前暂时先写死
							Double value = Double.parseDouble(functionInfo
									.getValue());
							String error=functionInfo.getAttr().getError();
							//logger.info("meterId :{},func id:{},value:{}",meterInfo.getAttr().getId(),functionInfo.getAttr().getId(),functionInfo.getValue());
							
							subTermCode = functionInfo.getAttr().getCoding();
							String id=functionInfo.getAttr().getId();
							switch (id) {
							case "01":
								meterLog.getData()
										.put("abcActivePower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("abcActivePower", error);
								}
								break;
							case "02":
								meterLog.getData().put("aActivePower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("aActivePower", error);
								}
								break;
							case "03":
								meterLog.getData().put("bActivePower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("bActivePower", error);
								}
								break;
							case "04":
								meterLog.getData().put("cActivePower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("cActivePower", error);
								}
								break;
							case "05":
								meterLog.getData().put("abcReactivePower",
										value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("abcReactivePower", error);
								}
								break;
							case "06":
								meterLog.getData()
										.put("aReactivePower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("aReactivePower", error);
								}
								break;
							case "07":
								meterLog.getData()
										.put("bReactivePower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("bReactivePower", error);
								}
								break;
							case "08":
								meterLog.getData()
										.put("cReactivePower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("cReactivePower", error);
								}
								break;
							case "09":
								meterLog.getData().put("abcApparentPower",
										value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("abcApparentPower", error);
								}
								break;
							case "10":
								meterLog.getData()
										.put("aApparentPower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("aApparentPower", error);
								}
								break;
							case "11":
								meterLog.getData()
										.put("bApparentPower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("bApparentPower", error);
								}
								break;
							case "12":
								meterLog.getData()
										.put("cApparentPower", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("cApparentPower", error);
								}
								break;
							case "13":
								meterLog.getData()
										.put("abcPowerFactor", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("abcPowerFactor", error);
								}
								break;
							case "14":
								meterLog.getData().put("aPowerFactor", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("aPowerFactor", error);
								}
								break;
							case "15":
								meterLog.getData().put("bPowerFactor", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("bPowerFactor", error);
								}
								break;
							case "16":
								meterLog.getData().put("cPowerFactor", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("cPowerFactor", error);
								}
								break;
							case "17":
								meterLog.getData().put(
										"positiveActivePowerMaxDemand", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveActivePowerMaxDemand", error);
								}
								break;
							case "18":
								meterLog.getData().put(
										"negativeActivePowerMaxDemand", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeActivePowerMaxDemand", error);
								}
							case "19":
								meterLog.getData().put(
										"positiveReactivePowerMaxDemand", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveReactivePowerMaxDemand", error);
								}
								break;
							case "20":
								meterLog.getData().put(
										"negativeReactivePowerMaxDemand", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeReactivePowerMaxDemand", error);
								}
								break;
							case "21":
								meterLog.getData().put("ua", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("ua", error);
								}
								break;
							case "22":
								meterLog.getData().put("ub", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("ub", error);
								}
								break;
							case "23":
								meterLog.getData().put("uc", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("uc", error);
								}
								break;
							case "24":
								meterLog.getData().put("ia", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("ia", error);
								}
								break;
							case "25":
								meterLog.getData().put("ib", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("ib", error);
								}
								break;
							case "26":
								meterLog.getData().put("ic", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("ic", error);
								}
								break;
							case "27":
								meterLog.getData().put(
										"positiveTotalActivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveTotalActivePowerCharge", error);
								}
								break;
							case "28":
								meterLog.getData().put(
										"positiveApexActivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveApexActivePowerCharge", error);
								}
								break;
							case "29":
								meterLog.getData().put(
										"positivePeakPowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positivePeakPowerCharge", error);
								}
								break;
							case "30":
								meterLog.getData().put(
										"positiveFlatPowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveFlatPowerCharge", error);
								}
								break;
							case "31":
								meterLog.getData().put(
										"positiveValleyPowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveValleyPowerCharge", error);
								}
								break;
							case "32":
								meterLog.getData().put(
										"negativeTotalActivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeTotalActivePowerCharge", error);
								}
								break;
							case "33":
								meterLog.getData().put(
										"negativeApexActivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeApexActivePowerCharge", error);
								}
								break;
							case "34":
								meterLog.getData().put(
										"negativePeakPowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativePeakPowerCharge", error);
								}
								break;
							case "35":
								meterLog.getData().put(
										"negativeFlatPowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeFlatPowerCharge", error);
								}
								break;
							case "36":
								meterLog.getData().put(
										"negativeValleyPowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeValleyPowerCharge", error);
								}
								break;
								// xxx
							case "37":
								meterLog.getData().put(
										"positiveTotalReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveTotalReactivePowerCharge", error);
								}
								break;
							case "38":
								meterLog.getData().put(
										"positiveApexReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveApexReactivePowerCharge", error);
								}
								break;
							case "39":
								meterLog.getData().put(
										"positivePeakReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positivePeakReactivePowerCharge", error);
								}
								break;
							case "40":
								meterLog.getData().put(
										"positiveFlatReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveFlatReactivePowerCharge", error);
								}
								break;
							case "41":
								meterLog.getData().put(
										"positiveValleyReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("positiveValleyReactivePowerCharge", error);
								}
								break;
							case "42":
								meterLog.getData().put(
										"negativeTotalReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeTotalReactivePowerCharge", error);
								}
								break;
							case "43":
								meterLog.getData().put(
										"negativeApexReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeApexReactivePowerCharge", error);
								}
								break;
							case "44":
								meterLog.getData().put(
										"negativePeakReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativePeakReactivePowerCharge", error);
								}
								break;
							case "45":
								meterLog.getData().put(
										"negativeFlatReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeFlatReactivePowerCharge", error);
								}
								break;
							case "46":
								meterLog.getData().put(
										"negativeValleyReactivePowerCharge", value);
								if(!"192".equals(error)){
									meterLog.getErrors().put("negativeValleyReactivePowerCharge", error);
								}
								break;
							}
							functionInfo.setValue(null);//取值后修改值为空，留下协议参数
							functionInfo.getAttr().setError(null);//取值后修改值为空，留下参数
						}
						//仪表主要为了保存仪表的配置信息，如果有变动重新保存一次，不需要每时每刻都保存的
						List<Object> rlist=funcionInfos.stream().sorted(new Comparator<FunctionInfo>(){
							@Override
							public int compare(FunctionInfo o1, FunctionInfo o2) {
								return Integer.parseInt(o1.getAttr().getId())-Integer.parseInt(o2.getAttr().getId());
							}
						}).collect(Collectors.toList());
						
						String functions=JSON.toJSONString(rlist,SerializerFeature.SortField);
						meter.setFunctions(functions);
						meter.setSubTermCode(subTermCode);
						String key="meter#functions#"+meter.getBuildingId()+meter.getGateWayId()+meter.getMeterId();
						String md5=Md5Utils.MD5(functions);
						if(jedisUtil.exists(key)){
							String fvalue=jedisUtil.get(key);
							if(!md5.equals(fvalue)){
								jedisUtil.setex(key, 30*60, md5);
								meters.add(meter);
							}
						}
						else{
							jedisUtil.setex(key, 30*60, md5);
							meters.add(meter);
						}
						meterLog.setSubTermCode(subTermCode);
						if(meterLog.isSuccessData()){
							meterLogs.add(meterLog);
						}
						else{
							dwLogger.log(DateUtils.format(meterLog.getReportTime(),DateUtils.DATE_TIME_PATTERN),meterLog.getMeterId(),JSON.toJSONString(meterLog));
						}
						
					}
					if(StringUtils.isBlank(logWriteType)||logWriteType.endsWith("kafka")){
						try{
							Result result = meterLogInfoService.pushMeterLog(meterLogs);// 增加List日志
							if (result.isSuccess()) {
								reportAck = "pass";
								meterLogInfoService.pushMeter(meters);
							} else {
								logger.error("pushMeterLog:{}", JSON.toJSONString(result));
							}
						}
						catch(RpcException e){
							//写入本地文件，flume去本地文件读取写入kafka
							if(reportAck.equals("fail")){
								for(MeterLogInfo meterLog:meterLogs){
									dwLogger.log(DateUtils.format(meterLog.getReportTime(),DateUtils.DATE_TIME_PATTERN),meterLog.getMeterId(),JSON.toJSONString(meterLog));
								}
								reportAck = "pass";
							}
						}
						
					}
					else{
						//写入文件
						for(MeterLogInfo meterLog:meterLogs){
							dwLogger.log(DateUtils.format(meterLog.getReportTime(),DateUtils.DATE_TIME_PATTERN),meterLog.getMeterId(),JSON.toJSONString(meterLog));
						}
						reportAck = "pass";
					}
					
				} else {
					reportAck = "fail";
					
				}
			}
			catch(Exception e){
				logger.error("netdau report error:{}",e);
			}
			CommonInfo common = new CommonInfo();
			common.setBuilding_id(req.getCommon().getBuilding_id());
			common.setGateway_id(req.getCommon().getGateway_id());
			common.setType("report_ack");
			resp.setCommon(common);
			ReportConfig reportConfig = new ReportConfig();
			Attribute attr = new Attribute();
			attr.setOperation("report_ack");
			reportConfig.setAttr(attr);
			reportConfig.setReport_ack(reportAck);
			resp.setReport_config(reportConfig);
			return SendResp.class;

		}
		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauQueryRequest req) {
			logger.debug("NetDauQueryRequest recv req : \r\n{}", req);
			// 从stc-remote的消息转发到access 然后 access转发到采集器网关
			Sender sender = TransportUtils.getSenderOf(req);
			/*
			 * Map<String,Map<String,AcceptInfo>>
			 * accepts=Configuration.getAccepts();
			 * Map<String,Map<String,ConnectInfo>>
			 * connects=Configuration.getConnects();
			 * Map<String,Map<String,ServerInfo>>
			 * servers=Configuration.getServers();
			 */
			return null;

		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauQueryResponse resp) {
			logger.debug("NetDauQueryResponse recv resp : \r\n{}", resp);
			// 接收到access的消息
			// 消息转发给stc-remote

			return null;

		}
	}
	public String getLogWriteType() {
		return logWriteType;
	}
	public void setLogWriteType(String logWriteType) {
		this.logWriteType = logWriteType;
	}
}
