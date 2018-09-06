package com.edianniu.pscp.das.meter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.edianniu.pscp.das.common.Constants;
import com.edianniu.pscp.das.meter.bean.MeterLogDo;
import com.edianniu.pscp.das.meter.domain.CompanyMeter;
import com.edianniu.pscp.das.util.BizUtils;
import com.edianniu.pscp.message.bean.WarningInfo;
import com.edianniu.pscp.message.bean.WarningType;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.message.service.dubbo.MeterLogInfoService;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerInfo;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.company.GetCompanyInfoResult;
import com.edianniu.pscp.mis.service.dubbo.CompanyCustomerInfoService;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;

import stc.skymobi.cache.redis.JedisUtil;

@Component
public class PushWarningAndMessageMethod {
	
	private static final Logger logger = LoggerFactory.getLogger(PushWarningAndMessageMethod.class);

	// 判断电流是否平衡的标准
	@Value(value = "${current.unbalance.standard}")
    private double currentUnbalanceStandard;
	
	private static final String WARNING_TAG="warning_tag#";
	private static final String MESSAGE_TAG="message_tag#";
	
	@Autowired
    private JedisUtil jedisUtil;
	@Autowired
	private MeterLogInfoService meterLogInfoService;
	@Autowired
	private MessageInfoService messageInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CompanyCustomerInfoService companyCustomerInfoService;
	
	/**
	 * 告警和信息处理
	 * @param meterLogDo
	 * @param companyMeter
	 */
	public void pushWarningAndMessage(MeterLogDo meterLogDo, CompanyMeter companyMeter) {
		List<WarningInfo> warnings = new ArrayList<>();
		List<MessageId> messageIds = new ArrayList<>();
		//1.判断电压是否异常
		String valtageOfNow = "A相:" + BizUtils.doubleToString(meterLogDo.getUa()) + 
							 ",B相:" + BizUtils.doubleToString(meterLogDo.getUb()) + 
							 ",C相:" + BizUtils.doubleToString(meterLogDo.getUc());
		if (meterLogDo.getuStatus() != Constants.U_STATUS_NORMAL) {
			if (meterLogDo.getuStatus() == Constants.U_STATUS_HEIGHT) {
				// 添加告警到List中，稍后做批量处理
				addWaring(warnings, meterLogDo, valtageOfNow, WarningType.VOLTAGE_HIGH.getValue());
				// 自动处理其他同类且与之相斥的告警（如果存在）
				dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_LOW.getValue());
				dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_ERROR.getValue());
			} else if (meterLogDo.getuStatus() == Constants.U_STATUS_LOWER) {
				addWaring(warnings, meterLogDo, valtageOfNow, WarningType.VOLTAGE_LOW.getValue());
				dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_HIGH.getValue());
				dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_ERROR.getValue());
			} else {
				addWaring(warnings, meterLogDo, valtageOfNow, WarningType.VOLTAGE_ERROR.getValue());
				dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_LOW.getValue());
				dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_HIGH.getValue());
			} 
			// 添加MessageID到List，并保存到redis，稍后做批量处理
			addMessageIds(messageIds, meterLogDo, MessageId.VOLTAGE_ABNORMAL_CUSTOMER);
			addMessageIds(messageIds, meterLogDo, MessageId.VOLTAGE_ABNORMAL_FACILITATOR);
		} else { 
			// 处理告警-->清除与电压相关的所有告警（如果存在）
			dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_LOW.getValue());
			dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_HIGH.getValue());
			dealWarning(warnings, meterLogDo, WarningType.VOLTAGE_ERROR.getValue());
			// 删除redis中MessageID记录
			rmvMessageIds(meterLogDo, MessageId.VOLTAGE_ABNORMAL_CUSTOMER);
			rmvMessageIds(meterLogDo, MessageId.VOLTAGE_ABNORMAL_FACILITATOR);
		}
		
		//2.判断功率因数是否异常
		String normalFactor = companyMeter.getNormalFactor();//正常功率因数的范围
		String[] factors = normalFactor.split("-");
		if (! (factors.length == 2 && BizUtils.isPositiveNumber(factors[0]) && BizUtils.isPositiveNumber(factors[1]))) {
			logger.error("功率因数配置异常:{}",companyMeter.getCompanyId());
			return;
		}
		Double abcPowerFactor = meterLogDo.getAbcPowerFactor();//三相总功率因数
		if (abcPowerFactor < Double.valueOf(factors[0])) { 
			addWaring(warnings, meterLogDo, BizUtils.doubleToString(abcPowerFactor), WarningType.POWER_FACTOR_ABNORMAL.getValue());
			
			addMessageIds(messageIds, meterLogDo, MessageId.POWER_FACTOR_ABNORMAL_CUSTOMER);
			addMessageIds(messageIds, meterLogDo, MessageId.POWER_FACTOR_ABNORMAL_FACILITATOR);
		} else { //处理告警，清除messageId
			dealWarning(warnings, meterLogDo, WarningType.POWER_FACTOR_ABNORMAL.getValue());
			rmvMessageIds(meterLogDo, MessageId.POWER_FACTOR_ABNORMAL_CUSTOMER);
			rmvMessageIds(meterLogDo, MessageId.POWER_FACTOR_ABNORMAL_FACILITATOR);
		}
		
		//3.判断负荷是否异常
		String economicLoad = companyMeter.getEconomicLoad();//经济负荷范围
		String[] loads = economicLoad.split("-");
		if (! (loads.length == 2 && BizUtils.isPositiveNumber(loads[0]) && BizUtils.isPositiveNumber(loads[1]))) {
			logger.error("负荷配置异常:{}",companyMeter.getCompanyId());
			return;
		}
		Double abcActivePower = meterLogDo.getAbcActivePower()*meterLogDo.getMultilpe()/1000D;//负荷 ---> 三相总有功功率
		if (abcActivePower < Double.valueOf(loads[0]) || abcActivePower > Double.valueOf(loads[1])) {
			if (abcActivePower < Double.valueOf(loads[0])) {
				addWaring(warnings, meterLogDo, BizUtils.doubleToString(abcActivePower), WarningType.LOAD_LOW.getValue());
				dealWarning(warnings, meterLogDo, WarningType.LOAD_HIGH.getValue());
			} else {
				addWaring(warnings, meterLogDo, BizUtils.doubleToString(abcActivePower), WarningType.LOAD_HIGH.getValue());
				dealWarning(warnings, meterLogDo, WarningType.LOAD_LOW.getValue());
			} 
			
			addMessageIds(messageIds, meterLogDo, MessageId.LOAD_ABNORMAL_CUSTOMER);
			addMessageIds(messageIds, meterLogDo, MessageId.LOAD_ABNORMAL_FACILITATOR);
		} else { 
			// 处理告警--->处理与负荷相关的所有告警
			dealWarning(warnings, meterLogDo, WarningType.LOAD_HIGH.getValue());
			dealWarning(warnings, meterLogDo, WarningType.LOAD_LOW.getValue());
			// 清除redis中messageId
			rmvMessageIds(meterLogDo, MessageId.LOAD_ABNORMAL_CUSTOMER);
			rmvMessageIds(meterLogDo, MessageId.LOAD_ABNORMAL_FACILITATOR);
		}
		
		//4.判断电流是否严重不平衡（电流失衡不需要推送消息）
		Double iUnbalanceDegree = meterLogDo.getiUnbalanceDegree();
		if (iUnbalanceDegree > currentUnbalanceStandard) { // 如果不平衡度大于10%(0.1)则电流不平衡
			addWaring(warnings, meterLogDo, BizUtils.doubleToString(iUnbalanceDegree), WarningType.CURRENT_UNBALANCE.getValue());
		} else { // 处理告警
			dealWarning(warnings, meterLogDo, WarningType.CURRENT_UNBALANCE.getValue());
		}
		
		//5.1 添加处理告警
		pushWarnings(warnings);
		//5.2  给客户服务商推送信息
		pushMessage(companyMeter.getCompanyId(), messageIds);
	}
	
	/**
	 * 添加告警
	 * @param warnings
	 * @param meterLog
	 * @param valueOfNow
	 * @param warningType
	 * @return
	 */
	private List<WarningInfo> addWaring(List<WarningInfo> warnings, MeterLogDo meterLogDo, String valueOfNow, Integer warningType) {
		// 放到缓存，防止同一仪表的同一类型告警重复添加
		long r = jedisUtil.setnx(WARNING_TAG + meterLogDo.getMeterId() + warningType, "1");
		if (r != 0) {
			WarningInfo warning = new WarningInfo();
			warning.setMeterId(meterLogDo.getMeterId());
			warning.setWarningType(warningType);
			warning.setOccurTime(meterLogDo.getReportTime());
			warning.setValueOfNow(valueOfNow);
			warning.setDealStatus(Constants.TAG_NO);
			warnings.add(warning);
		}
		return warnings;
	}
	
	/**
	 * 处理告警
	 * @param warnings
	 * @param meterLog
	 * @param warningType
	 * @return
	 */
	private List<WarningInfo> dealWarning(List<WarningInfo> warnings, MeterLogDo meterLogDo, Integer warningType){
		// 先判断缓存中是否存在告警记录，如果不存在则不添加到List中
		if (jedisUtil.exists(WARNING_TAG + meterLogDo.getMeterId() + warningType)) {
			WarningInfo warning = new WarningInfo();
			warning.setMeterId(meterLogDo.getMeterId());
			warning.setWarningType(warningType);
			warning.setRevertTime(meterLogDo.getReportTime());
			warning.setDealStatus(Constants.TAG_YES);
			warnings.add(warning);
			// 删除缓存中告警记录
			jedisUtil.del(WARNING_TAG+meterLogDo.getMeterId()+warningType);
		}
		return warnings;
	}
	
	/**
	 * 将MessageId按顺序放到List集合，并在redis中留有记录
	 */
	private List<MessageId> addMessageIds(List<MessageId> messageIdList, MeterLogDo meterLogDo, MessageId messageId){
		Long r = jedisUtil.setnx(MESSAGE_TAG + meterLogDo.getMeterId() + messageId.getValue(), "1");
		if (r != 0) {
			messageIdList.add(messageId);
		}
		return messageIdList;
	}
	
	/**
	 * 删除redis中的MessageId记录
	 */
	private void rmvMessageIds(MeterLogDo meterLogDo, MessageId messageId){
		if (jedisUtil.exists(MESSAGE_TAG + meterLogDo.getMeterId() + messageId.getValue())) {
			jedisUtil.del(MESSAGE_TAG + meterLogDo.getMeterId() + messageId.getValue());
		}
	}
	
	/**
	 * 调用message的dubbo接口，添加处理告警
	 * @param warnings
	 */
	private void pushWarnings(final List<WarningInfo> warnings){
		try {
			if (CollectionUtils.isEmpty(warnings)) {
				return;
			}
			EXECUTOR_SERVICE.submit(new Runnable() {
				@Override
				public void run() {
					meterLogInfoService.pushWarningInfo(warnings);
				}
			});
		} catch (Exception e) {
			logger.error("向消息服务推送告警信息异常", e);
		}
	}
	
	/**
	 * 调用message的dubbo接口，给客户和服务商推送短信
	 * @param companyId  客户公司的companyId
	 * @param messageIds 包含偶数个元素，奇数位元素是客户的MessageId，偶数位元素是服务商的MessageId
	 */
	private void pushMessage(final Long companyId, final List<MessageId> messageIds){
		try {
			EXECUTOR_SERVICE.submit(new Runnable() {
				@Override
				public void run() {
					if (null == companyId || null == messageIds || messageIds.size() % 2 != 0) {
						return;
					}
					
					// 客户信息
					GetCompanyInfoResult companyInfoResult = companyInfoService.getCompanyInfo(companyId);
					if (! companyInfoResult.isSuccess()) {
						return;
					}
					CompanyInfo companyInfo = companyInfoResult.getCompanyInfo();
					if (null == companyInfo) {
						return;
					}
					HashMap<String, String> params = new HashMap<>();
					params.put("company_name", companyInfo.getName());
					// 给客户推送信息，messageID位于List的奇数位置
					for (int i = 0; i < messageIds.size(); i = i + 2) {
						messageInfoService.sendSmsAndPushMessage(companyInfo.getMemberId(), 
								companyInfo.getMobile(), messageIds.get(i), params);
					}
					
					// 服务商信息
					List<CompanyCustomerInfo> companyCustomerInfoList = companyCustomerInfoService.getInfo(companyInfo.getMemberId(), null);
					for (CompanyCustomerInfo companyCustomerInfo : companyCustomerInfoList) {
						Long facilitatorCompanyId = companyCustomerInfo.getCompanyId();
						GetCompanyInfoResult result = companyInfoService.getCompanyInfo(facilitatorCompanyId);
						if (!result.isSuccess()) {
							break;
						}
						CompanyInfo facilitator = result.getCompanyInfo();
						if (null == facilitator) {
							break;
						}
						// 给服务商推送消息，messageID位于List的偶数位置
						for (int j = 1; j < messageIds.size(); j = j + 2) {
							messageInfoService.sendSmsAndPushMessage(facilitator.getMemberId(), 
									facilitator.getMobile(), messageIds.get(j), params);
						}
					}
					
				}
			});
		} catch (Exception e) {
			logger.error("向客户服务商推送信息异常", e);
		}
	}
	
	// 创建一个固定线程池
    private final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<Runnable>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());

}
