package com.edianniu.pscp.mis.biz.log;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/*import stc.skymobi.common.AcceptInfo;
import stc.skymobi.common.Configuration;
import stc.skymobi.common.ConnectInfo;
import stc.skymobi.common.ServerInfo;*/
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.GateWayStatus;
import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.service.dubbo.MeterLogInfoService;
import com.edianniu.pscp.mis.bean.log.Attribute;
import com.edianniu.pscp.mis.bean.log.CommonInfo;
import com.edianniu.pscp.mis.bean.log.DeviceAckInfo;
import com.edianniu.pscp.mis.bean.request.log.NetDauDeviceRequest;
import com.edianniu.pscp.mis.bean.request.log.NetDauOfflineRequest;
import com.edianniu.pscp.mis.bean.response.log.NetDauDeviceResponse;
import com.edianniu.pscp.mis.biz.NetDauBiz;
import com.edianniu.pscp.mis.util.ReflectionUtils;

/**
 * netdau采集器online接口
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:10:05
 * @version V1.0
 */
public class NetDauDeviceBiz extends
		NetDauBiz<NetDauDeviceRequest, NetDauDeviceResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(NetDauDeviceBiz.class);
	@Autowired
	private MeterLogInfoService meterLogInfoService;
	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}
		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauOfflineRequest req) {
			logger.debug("NetDauOfflineRequest recv req : \r\n{}", req);
			//收到网关下线通知，更新网关状态，并且更新网关下的仪表的状态
			GateWayInfo gateWay=new GateWayInfo();
			gateWay.setType(1);//netdau
			gateWay.setGatewayId(req.getCommon().getGateway_id());
			gateWay.setBuildingId(req.getCommon().getBuilding_id());
			gateWay.setStatus(GateWayStatus.OFFLINE.getValue());//下线
			Result result=meterLogInfoService.pushGateWay(gateWay);
			if(!result.isSuccess()){
				logger.error("push gateWay error:{}",JSON.toJSONString(result));
			}
			return null;
			
		}
		//设备上线，上传设备信息，仪表信息上传时去比对一下设备是否上线，然后更新仪表状态
		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauDeviceRequest req) {
			logger.debug("NetDauOnlineRequest recv req : \r\n{}", req);
			NetDauDeviceResponse resp = (NetDauDeviceResponse) initResponse(
					ctx, req, new NetDauDeviceResponse());
			GateWayInfo gateWay=new GateWayInfo();
			gateWay.setType(1);//netdau
			gateWay.setBuildingId(req.getCommon().getBuilding_id());
			gateWay.setGatewayId(req.getCommon().getGateway_id());
			gateWay.setStatus(GateWayStatus.ONLINE.getValue());//上线
			gateWay.setReportTime(new Date());
			try {
				ReflectionUtils.beanToMap(req.getDevice(), gateWay.getGateWayData());
				Result result=meterLogInfoService.pushGateWay(gateWay);
				if(!result.isSuccess()){
					logger.error("push gateWay error:{}",JSON.toJSONString(result));
				}
			} catch (Exception e) {
				logger.error("beanToMap:{}",e);
			}
			CommonInfo common=new CommonInfo();
			BeanUtils.copyProperties(req.getCommon(), common);
			common.setType("device_ack");
			resp.setCommon(common);
			DeviceAckInfo device=new DeviceAckInfo();
			device.setDevice_ack("pass");
			Attribute attr=new Attribute();
			attr.setOperation("device_ack");
			device.setAttr(attr);
			resp.setDevice(device);
			return SendResp.class;
		}
		
	}
}
