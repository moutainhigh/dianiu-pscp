package com.edianniu.pscp.mis.biz.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import stc.skymobi.cache.redis.JedisUtil;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
import stc.skymobi.netty.transport.Sender;
import stc.skymobi.netty.transport.SenderUtils;

import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.commons.ResultCode;
import com.edianniu.pscp.message.service.dubbo.MeterLogInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.log.Attribute;
import com.edianniu.pscp.mis.bean.log.CommonInfo;
import com.edianniu.pscp.mis.bean.log.Control;
import com.edianniu.pscp.mis.bean.log.ControlInfo;
import com.edianniu.pscp.mis.bean.log.InstructionInfo;
import com.edianniu.pscp.mis.bean.log.MeterVO;
import com.edianniu.pscp.mis.bean.request.log.NetDauControlAckRequest;
import com.edianniu.pscp.mis.bean.request.log.NetDauControlRequest;
import com.edianniu.pscp.mis.bean.request.log.NetDauControlToTerminalRequest;
import com.edianniu.pscp.mis.bean.response.log.NetDauControlResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.domain.CompanyRenter;
import com.edianniu.pscp.mis.domain.CompanyRenterMeter;
import com.edianniu.pscp.mis.service.CompanyRenterService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.util.DTL645V2007;

/**
 * 
 * netdau透传命令接口
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:10:05
 * @version V1.0
 */
public class NetDauControlBiz extends
		BaseBiz<NetDauControlRequest, NetDauControlResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(NetDauControlBiz.class);
	@Autowired
	private MeterLogInfoService meterLogInfoService;
	@Autowired
	private CompanyRenterService companyRenterService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private JedisUtil jedisUtil;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauControlRequest req) {// 接收remote请求
			logger.debug("NetDauControlRequest recv req : \r\n{}", req);
			NetDauControlResponse resp = (NetDauControlResponse) initResponse(
					ctx, req, new NetDauControlResponse());
			String meterId=req.getMeterId();
			Long renterId=req.getRenterId();
			try{
				Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
				if(isLogin.getResultCode()!=ResultCode.SUCCESS){
					resp.setResultCode(isLogin.getResultCode());
					resp.setResultMessage(isLogin.getResultMessage());
					return SendResp.class;
				}
				GetUserInfoResult uinfoResult=userInfoService.getUserInfo(req.getUid());
				if(!uinfoResult.isSuccess()){
					resp.setResultCode(uinfoResult.getResultCode());
					resp.setResultMessage(uinfoResult.getResultMessage());
					return SendResp.class;
				}
				if(req.getRenterId()==null){
					resp.setResultCode(ResultCode.ERROR_201);
					resp.setResultMessage("renterId不能为空");
					return SendResp.class;
				}
				CompanyRenter renter=companyRenterService.getById(req.getRenterId());
				if(renter==null){
					resp.setResultCode(ResultCode.ERROR_201);
					resp.setResultMessage("租客不存在");
					return SendResp.class;
				}
				if (StringUtils.isBlank(req.getCmd())) {
					resp.setResultCode(ResultCode.ERROR_201);
					resp.setResultMessage("cmd不能为空");
					return SendResp.class;
				}
				if(!("closing".equals(req.getCmd())||"tripping".equals(req.getCmd()))){
					resp.setResultCode(ResultCode.ERROR_201);
					resp.setResultMessage("cmd 不正确");
					return SendResp.class;
				}
				if (StringUtils.isBlank(req.getMeterId())) {
					resp.setResultCode(ResultCode.ERROR_201);
					resp.setResultMessage("meterId不能为空");
					return SendResp.class;
				}
				if(req.getMeterId().length()!=15){
					resp.setResultCode(ResultCode.ERROR_201);
					resp.setResultMessage("仪表ID"+req.getMeterId()+"格式错误，必须15位");
					return SendResp.class;
				}
				String cmdType="000";
				if(req.getCmd().equals("closing")){
					cmdType="001";
				}
				else if(req.getCmd().equals("tripping")){
					cmdType="002";
				}
				CompanyRenterMeter renterMeter=companyRenterService.getRenterMeter(req.getRenterId(), meterId);
				if(renterMeter==null){
					resp.setResultCode(ResultCode.ERROR_401);
					resp.setResultMessage(meterId+" 不存在");
					return SendResp.class;
				}
				String key=CacheKey.CACHE_KEY_NETDAU_CONTROL_SWITCH+renterId+"#"+meterId;
				if(jedisUtil.exists(key)){
					resp.setResultCode(ResultCode.ERROR_401);
					resp.setResultMessage("操作执行中，请勿重复操作");
					return SendResp.class;
				}
				String buildingNo=meterId.substring(0, 10);
				String gatewayNo=meterId.substring(10, 12);
				String meterNo=meterId.substring(12, 15);
				
				
				MeterVO meterVO=new MeterVO();
				meterVO.setBuildingNo(buildingNo);
				meterVO.setGatewayNo(gatewayNo);
				meterVO.setMeterNo(meterNo);
				Sender sender = SenderUtils.getByTerminalNo(meterVO.getBuildingNo()+"#"
						+ meterVO.getGatewayNo());
				if (sender == null) {
					resp.setResultCode(ResultCode.ERROR_401);
					resp.setResultMessage(meterId+"终端掉线");
					return SendResp.class;
				}
				MeterInfo meterInfo = meterLogInfoService.getMeterInfo(
						meterVO.getBuildingNo(), meterVO.getGatewayNo(), meterVO.getMeterNo());
				if (meterInfo != null) {
					meterVO.setAddress((String) meterInfo.getMeterData().get("address"));
				}
				NetDauControlToTerminalRequest netDauControlToTerminalRequest = new NetDauControlToTerminalRequest();
				CommonInfo common = new CommonInfo();
				common.setBuilding_id(meterVO.getBuildingNo());
				common.setGateway_id(meterVO.getGatewayNo());
				common.setType("control");
				netDauControlToTerminalRequest.setCommon(common);
				InstructionInfo instruction = new InstructionInfo();
				ControlInfo controlInfo = new ControlInfo();
				List<Control> controls = new ArrayList<>();
				Control control = new Control();
				Attribute attr = new Attribute();
				attr.setMeterId(meterVO.getMeterNo());// 对应的仪表ID
				attr.setTimeout("5000");// 仪表响应最大时间（毫秒），不填该属性时默认为100毫秒 (100 ~
										// 65536)
				attr.setRetry("3");// 重发次数,不填该属性时默认为1次,重发间隔1秒钟 (100 ~ 1000)
				String cmd = "";
				String address =meterVO.getAddress();// 从pscp_meter_info里获取仪表地址
				String operationCode = "78563412";
				
				// 68H A0 … A5 68H 1CH L PA P0 P1 P2 C0 … C3 N1 … Nm CS 16
				// 68H 起始符
				// A0 … A5 仪表地址
				// 1CH 控制码
				// L 数据域长度 PA到Nm之间的长度
				// PA
				
				if (req.getCmd().equals("closing")) {//合闸
					cmd = DTL645V2007.getCMD(address, "1C", "00",
							operationCode, "1C");
				} else if (req.getCmd().equals("tripping")) {// 跳闸
					cmd = DTL645V2007.getCMD(address, "1C", "00",
							operationCode, "1A");
				}
				String idx=req.getRenterId()+meterVO.getMeterNo()+cmdType;
				attr.setIdx(idx);// 透传命令序号
				
				attr.setCmd(cmd);// 控制命令，格式16进制字符串，2个字符表示一个字节采集器不解析该段数据，直接发送给仪表id对应的仪表
				attr.setSize(cmd.length() / 2 + "");// 透传命令长度，为cmd后跟的字符串长度除以2
				control.setAttr(attr);
				controls.add(control);
				controlInfo.setControl(controls);
				Attribute attr1=new Attribute();
				attr.setOperation("control");
				instruction.setAttr(attr1);
				instruction.setControl_info(controlInfo);
				netDauControlToTerminalRequest.setInstruction(instruction);
				sender.send(netDauControlToTerminalRequest);
				jedisUtil.set(key, meterId);
				if(jedisUtil.exists(key)){
					jedisUtil.expire(key, 2*60);
				}
			}
			catch(Exception e){
				logger.error("netdau_control:{}",e);
				resp.setResultCode(ResultCode.ERROR_500);
				resp.setResultMessage("系统异常");
				return SendResp.class;
			}
			
			return SendResp.class;
		}
		
		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauControlAckRequest req) {// 采集器网关返回控制命令应答
			logger.debug("NetDauControlAckRequest recv : \r\n{}", req);
			String buildingId=req.getCommon().getBuilding_id();
			String gateWayId=req.getCommon().getGateway_id();
			Control control=req.getInstruction().getControl_info().getControl_ack().get(0);
			String idx=control.getAttr().getIdx();
			String data=control.getAttr().getData();
			String meterNo=control.getAttr().getMeterId();
			for(int i=0;i<3-control.getAttr().getMeterId().length();i++){
				meterNo="0"+meterNo;
			}
			Long renterId=Long.parseLong(idx.substring(0, idx.length()-6));
			String cmdType=idx.substring(idx.length()-3, idx.length());
			String err=control.getAttr().getErr();
			String meterId=buildingId+gateWayId+meterNo;
			String type="未知";
			int switchStatus=0;//1 合闸，0 跳闸
			String cmd="";
			if(cmdType.equals("001")){
				type="合闸";
				switchStatus=1;
				cmd="switch";
			}
			else if(cmdType.equals("002")){
				type="跳闸";
				switchStatus=0;
				cmd="switch";
			}
			String key=CacheKey.CACHE_KEY_NETDAU_CONTROL_SWITCH+renterId+"#"+meterId;
			if(err.equals("F0")){//通信正常
				//修改租客配置表switch_staus
				//修改租客仪表信息表status状态
				companyRenterService.updateRenterSwitchStatus(renterId, meterId, switchStatus);
				logger.info("仪表：{},"+type+"成功 , err:{} data:{}",meterId,err,data);
			}
			else{
				logger.info("仪表：{},"+type+"失败 , err:{} data:{}",meterId,err,data);
			}
			jedisUtil.del(key);
			return null;
		}
	}
	@StateTemplate
	class Rscp {
		Rscp() {
		}
		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				NetDauControlAckRequest req) {// 采集器网关返回控制命令应答
			logger.debug("NetDauControlAckRequest recv : \r\n{}", req);
			String buildingId=req.getCommon().getBuilding_id();
			String gateWayId=req.getCommon().getGateway_id();
			Control control=req.getInstruction().getControl_info().getControl_ack().get(0);
			String idx=control.getAttr().getIdx();
			String data=control.getAttr().getData();
			String meterId=control.getAttr().getMeterId();
			String err=control.getAttr().getErr();
			//更新每个仪表的拉闸状态
			//判断某个租客的拉闸仪表是否全部搞定
			//更新租客信息表的拉闸状态
			return null;
		}
	}
}
