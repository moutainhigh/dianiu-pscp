package com.edianniu.pscp.message.meter.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.message.bean.GateWayInfo;
import com.edianniu.pscp.message.bean.GateWayStatus;
import com.edianniu.pscp.message.bean.MeterInfo;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.bean.WarningType;
import com.edianniu.pscp.message.commons.Constants;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.meter.dao.CompanyMeterDao;
import com.edianniu.pscp.message.meter.dao.GateWayDao;
import com.edianniu.pscp.message.meter.dao.MeterDao;
import com.edianniu.pscp.message.meter.domain.CompanyMeter;
import com.edianniu.pscp.message.meter.domain.GateWay;
import com.edianniu.pscp.message.meter.domain.Meter;
import com.edianniu.pscp.message.meter.service.MeterService;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.message.meter.dao.WarningDao;
import com.edianniu.pscp.message.meter.domain.Warning;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerInfo;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.company.GetCompanyInfoResult;
import com.edianniu.pscp.mis.bean.user.QuerySysUserReq;
import com.edianniu.pscp.mis.bean.user.SysUserInfo;
import com.edianniu.pscp.mis.service.dubbo.CompanyCustomerInfoService;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;
import com.edianniu.pscp.mis.service.dubbo.SysUserInfoService;
import com.edianniu.pscp.search.util.DateUtils;

/**
 * MeterServiceImpl
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月27日 下午4:39:59
 * @version V1.0
 */
@Service
@Repository("meterService")
public class MeterServiceImpl implements MeterService {
	private static final Logger logger = LoggerFactory.getLogger(MeterServiceImpl.class);
	@Autowired
	private CompanyMeterDao companyMeterDao;
	@Autowired
	private MeterDao meterDao;
	@Autowired
	private GateWayDao gateWayDao;
	@Autowired
	private WarningDao warningDao;
	@Autowired
	private MessageInfoService messageInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CompanyCustomerInfoService companyCustomerInfoService;
	@Autowired
	private SysUserInfoService sysUserInfoService;

	@Override
	public CompanyMeter getCompanyMeterById(String meterId) {
		return companyMeterDao.getByMeterId(meterId);
	}

	@Override
	public Meter getMeterById(Long id) {
		return meterDao.getById(id);
	}

	@Override
	public Meter getMeter(String buildingId, String gatewayId, String meterId, Integer type) {
		return meterDao.get(buildingId, gatewayId, meterId, type);
	}

	@Override
	public int saveMeter(Meter meter) {
		return meterDao.save(meter);
	}

	@Override
	public int updateMeter(Meter meter) {
		return meterDao.update(meter);
	}

	@Override
	public int updateMeterStatus(Meter meter) {
		return meterDao.updateStatus(meter);
	}

	@Override
	public GateWay getGateWayById(Long id) {
		return gateWayDao.getById(id);
	}

	@Override
	public GateWay getGateWay(String buildingId, String gatewayId) {
		return gateWayDao.get(buildingId, gatewayId);
	}

	@Override
	public int saveGateWay(GateWay gateWay) {
		return gateWayDao.save(gateWay);
	}

	@Override
	public int updateGateWay(GateWay gateWay) {
		return gateWayDao.update(gateWay);
	}

	@Override
	public int updateGateWayStatus(GateWay gateWay) {
		return gateWayDao.updateStatus(gateWay);
	}

	@Override
	@Transactional
	public void handleGateWay(GateWayInfo gateWayInfo) {
		Date date = new Date();
		if (gateWayInfo.getStatus() == GateWayStatus.OFFLINE.getValue()) {// 下线
			// 更改设备状态，更改设备关联的仪表的状态
			GateWay gateWay = new GateWay();
			gateWay.setBuildingId(gateWayInfo.getBuildingId());
			gateWay.setGatewayId(gateWayInfo.getGatewayId());
			gateWay.setStatus(GateWayStatus.OFFLINE.getValue());
			gateWay.setModifiedTime(date);
			this.updateGateWayStatus(gateWay);
			Meter meter = new Meter();
			meter.setBuildingId(gateWayInfo.getBuildingId());
			meter.setGatewayId(gateWayInfo.getGatewayId());
			meter.setStatus(GateWayStatus.OFFLINE.getValue());
			meter.setType(gateWayInfo.getType());
			meter.setModifiedTime(date);
			this.updateMeterStatus(meter);
			// 更新公司仪表的状态
			List<Meter> meters = meterDao.getList(gateWayInfo.getBuildingId(), gateWayInfo.getGatewayId(),
					gateWayInfo.getType());
			Long companyId = 0L;
			if (meters != null && !meters.isEmpty()) {
				for (Meter meter1 : meters) {
					CompanyMeter companyMeter = getCompanyMeterById(meter1.getMeterNo());
					if (companyMeter != null) {
						companyId = companyMeter.getCompanyId();
						companyMeter.setStatus(GateWayStatus.OFFLINE.getValue());
						companyMeter.setModifiedTime(date);
						companyMeterDao.updateStatus(companyMeter);
					}
				}
			}
			// 添加告警到告警列表
			Warning warning = new Warning();
			warning.setCompanyId(companyId);
			warning.setWarningObject(gateWayInfo.getBuildingId() + gateWayInfo.getGatewayId());
			warning.setWarningType(WarningType.GATEWAY_OFFLINE.getValue());
			warning.setOccurTime(date);
			warning.setDealStatus(Constants.TAG_NO);
			warning.setReadStatus(Constants.TAG_NO);
			warning.setDescription("编号为" + gateWayInfo.getBuildingId() + gateWayInfo.getGatewayId() + "网关设备已掉线");
			warningDao.save(warning);
			// 给客户|服务商|运维 推送信息
			sendMessagePush(gateWayInfo);
		} else if (gateWayInfo.getStatus() == GateWayStatus.ONLINE.getValue()) {// 上线
			// 更改设备状态以及设备信息，更改设备关联的仪表的状态
			GateWay gateWay = this.getGateWay(gateWayInfo.getBuildingId(), gateWayInfo.getGatewayId());
			if (gateWay == null) {
				gateWay = new GateWay();
				try {
					BeanUtils.populate(gateWay, gateWayInfo.getGateWayData());
				} catch (Exception e) {
					logger.error("BeanUtils.populate:{}", e);
				}
				gateWay.setBuildingName((String) gateWayInfo.getGateWayData().get("build_name"));
				gateWay.setBeginTime(
						DateUtils.parse((String) gateWayInfo.getGateWayData().get("begin_time"), "yyyyMMddHHmm"));
				gateWay.setBuildNo((String) gateWayInfo.getGateWayData().get("build_no"));
				gateWay.setDevNo((String) gateWayInfo.getGateWayData().get("dev_no"));
				gateWay.setDevNum(Integer.parseInt((String) gateWayInfo.getGateWayData().get("dev_num")));
				gateWay.setBuildingId(gateWayInfo.getBuildingId());
				gateWay.setGatewayId(gateWayInfo.getGatewayId());
				gateWay.setStatus(GateWayStatus.ONLINE.getValue());
				gateWay.setReportTime(gateWayInfo.getReportTime());
				saveGateWay(gateWay);
			} else {
				try {
					BeanUtils.populate(gateWay, gateWayInfo.getGateWayData());
				} catch (Exception e) {
					logger.error("BeanUtils.populate:{}", e);
				}
				gateWay.setBuildingName((String) gateWayInfo.getGateWayData().get("build_name"));
				gateWay.setBeginTime(
						DateUtils.parse((String) gateWayInfo.getGateWayData().get("begin_time"), "yyyyMMddHHmm"));
				gateWay.setBuildNo((String) gateWayInfo.getGateWayData().get("build_no"));
				gateWay.setDevNo((String) gateWayInfo.getGateWayData().get("dev_no"));
				gateWay.setDevNum(Integer.parseInt((String) gateWayInfo.getGateWayData().get("dev_num")));
				gateWay.setBuildingId(gateWayInfo.getBuildingId());
				gateWay.setGatewayId(gateWayInfo.getGatewayId());
				gateWay.setStatus(GateWayStatus.ONLINE.getValue());
				gateWay.setReportTime(gateWayInfo.getReportTime());
				gateWay.setModifiedTime(date);
				updateGateWay(gateWay);
			}
			// 更新以下仪表的状态
			Meter meter = new Meter();
			meter.setBuildingId(gateWayInfo.getBuildingId());
			meter.setGatewayId(gateWayInfo.getGatewayId());
			meter.setStatus(GateWayStatus.ONLINE.getValue());
			meter.setType(gateWayInfo.getType());
			meter.setModifiedTime(date);
			this.updateMeterStatus(meter);
			// 更新公司仪表的状态
			List<Meter> meters = meterDao.getList(gateWayInfo.getBuildingId(), gateWayInfo.getGatewayId(),
					gateWayInfo.getType());
			if (meters != null && !meters.isEmpty()) {
				for (Meter meter1 : meters) {
					CompanyMeter companyMeter = getCompanyMeterById(meter1.getMeterNo());
					if (companyMeter != null) {
						companyMeter.setStatus(GateWayStatus.ONLINE.getValue());
						companyMeter.setModifiedTime(date);
						companyMeterDao.updateStatus(companyMeter);
					}
				}
			}
			// 更新告警表的告警处理状态
			Warning warning = new Warning();
			warning.setWarningObject(gateWayInfo.getBuildingId() + gateWayInfo.getGatewayId());
			warning.setWarningType(WarningType.GATEWAY_OFFLINE.getValue());
			warning.setDealStatus(Constants.TAG_YES);
			warning.setRevertTime(date);
			warning.setModifiedTime(date);
			warningDao.updateDealStatus(warning);
		} else {
			logger.warn("gate way status not support {}", gateWayInfo.getStatus());
		}
	}
	
	/**
	 * 设备掉线发送消息  客户|服务商|运维
	 * @param gateWayInfo
	 */
	public void  sendMessagePush(final GateWayInfo gateWayInfo) {
		if (null == gateWayInfo) {
			return;
		}
		try {
			EXECUTOR_SERVICE.submit(new Runnable() {
				@Override
				public void run() {
					String gatewayId = gateWayInfo.getGatewayId();
					String buildingId = gateWayInfo.getBuildingId();
					Integer type = gateWayInfo.getType();
					List<Meter> meterList = meterDao.getList(buildingId, gatewayId, type);
					if (CollectionUtils.isEmpty(meterList)) {
						return;
					}
					
					// 客户信息
					Long companyId = 0L;
					for (Meter meter : meterList) {
						CompanyMeter companyMeter = getCompanyMeterById(meter.getMeterNo());
						if (null != companyMeter) {
							companyId = companyMeter.getCompanyId();
							if (null != companyId && 0L != companyId) {// 同一网关下的仪表，都属于同一个公司，即companyId相同
								break;
							}
						}
					}
					GetCompanyInfoResult companyInfoResult = companyInfoService.getCompanyInfo(companyId);
					if (! companyInfoResult.isSuccess()) {
						return;
					}
					CompanyInfo companyInfo = companyInfoResult.getCompanyInfo();
					if (null == companyInfo) {
						return;
					}
					MessageId messageId = MessageId.GATEWAY_OFF_LINE;
					Map<String, String> params = new HashMap<>();
					params.put("gateway_id", gateWayInfo.getBuildingId() + "#" + gateWayInfo.getGatewayId()); // 楼宇编号#网关编号
					// 给客户推送信息
					messageInfoService.sendSmsAndPushMessage(companyInfo.getMemberId(), companyInfo.getMobile(), messageId, params);
					
					// 服务商信息
					List<CompanyCustomerInfo> infoList = companyCustomerInfoService.getInfo(companyInfo.getMemberId(), null);
					if (CollectionUtils.isNotEmpty(infoList)) {
						for (CompanyCustomerInfo companyCustomerInfo : infoList) {
							Long companyId1 = companyCustomerInfo.getCompanyId();
							GetCompanyInfoResult result1 = companyInfoService.getCompanyInfo(companyId1);
							if (! result1.isSuccess()) {
								break;
							}
							CompanyInfo companyInfo1 = result1.getCompanyInfo();
							if (null == companyInfo1) {
								break;
							}
							// 给服务商推送信息
							messageInfoService.sendSmsAndPushMessage(companyInfo1.getMemberId(), companyInfo1.getMobile(), messageId, params);
						}
					}
					
					// 短信通知运维 
					QuerySysUserReq req = new QuerySysUserReq();
					req.setOffLineNotice(Constants.TAG_YES);
					req.setStatus(Constants.TAG_YES);
					List<SysUserInfo> sysUserInfoList = sysUserInfoService.getList(req);
					if (CollectionUtils.isNotEmpty(sysUserInfoList)) {
						for (SysUserInfo sysUserInfo : sysUserInfoList) {
							messageInfoService.sendSmsMessage(sysUserInfo.getUserId(), sysUserInfo.getMobile(), messageId, params);
						}
					}
				}
			});
		} catch (Exception e) {
			logger.error("设备掉线推送消息异常", e);
		}
	}
	
	/**
     * 创建一个固定线程池
     */
	private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
			1, Runtime.getRuntime().availableProcessors(), 
			60, TimeUnit.SECONDS, 
			// 工作队列
			new SynchronousQueue<Runnable>(),
			// 线程饱和处理策略
			new ThreadPoolExecutor.CallerRunsPolicy());

	@Override
	@Transactional
	public void handleMeter(MeterInfo meterInfo) {
		Meter meter = this.getMeter(meterInfo.getBuildingId(), meterInfo.getGateWayId(), meterInfo.getMeterId(),
				meterInfo.getType());
		if (meter == null) {
			meter = new Meter();
			meter.setAddress((String) meterInfo.getMeterData().get("address"));
			meter.setBuildingId(meterInfo.getBuildingId());
			meter.setGatewayId(meterInfo.getGateWayId());
			meter.setMeterId(meterInfo.getMeterId());
			meter.setFunctions(meterInfo.getFunctions());
			meter.setName((String) meterInfo.getMeterData().get("name"));
			meter.setReportTime(meterInfo.getReportTime());
			meter.setStatus(GateWayStatus.ONLINE.getValue());
			meter.setType(meterInfo.getType());
			meter.setSubTermCode(meterInfo.getSubTermCode());
			this.saveMeter(meter);
		} else {
			meter.setAddress((String) meterInfo.getMeterData().get("address"));
			meter.setFunctions(meterInfo.getFunctions());
			meter.setName((String) meterInfo.getMeterData().get("name"));
			meter.setReportTime(meterInfo.getReportTime());
			meter.setStatus(GateWayStatus.ONLINE.getValue());
			meter.setType(meterInfo.getType());
			meter.setSubTermCode(meterInfo.getSubTermCode());
			this.updateMeter(meter);
		}
		CompanyMeter companyMeter = getCompanyMeterById(meter.getMeterNo());
		if (companyMeter != null) {
			companyMeter.setStatus(GateWayStatus.ONLINE.getValue());
			companyMeterDao.updateStatus(companyMeter);
		}
	}

	@Override
	@Transactional
	public void handleWarning(WarningInfo warningInfo) {
		CompanyMeter companyMeter = companyMeterDao.getByMeterId(warningInfo.getMeterId());
		if (null == companyMeter) {
			return;
		}
		
		Warning warning = new Warning();
		warning.setCompanyId(companyMeter.getCompanyId());
		warning.setWarningObject(warningInfo.getMeterId());
		warning.setWarningType(warningInfo.getWarningType());
		if (warningInfo.getDealStatus().equals(Constants.TAG_NO)) { //新增告警
			warning.setOccurTime(warningInfo.getOccurTime());
			warning.setDescription("当前值为" + warningInfo.getValueOfNow() + "，不在正常范围");
			warning.setDealStatus(Constants.TAG_NO);
			warning.setReadStatus(Constants.TAG_NO);
			warningDao.save(warning);
		} else if (warningInfo.getDealStatus().equals(Constants.TAG_YES)){ //处理告警
			Date date = new Date();
			warning.setRevertTime(warningInfo.getRevertTime() == null ? warningInfo.getRevertTime() : date);
			warning.setDealStatus(Constants.TAG_YES);
			warning.setModifiedTime(date);
			warningDao.updateDealStatus(warning);
		} else {
			logger.warn("warning dealStatus not support {}", warningInfo.getDealStatus());
		}
	}

}
