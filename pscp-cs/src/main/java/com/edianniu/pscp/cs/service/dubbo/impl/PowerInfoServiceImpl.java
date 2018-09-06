package com.edianniu.pscp.cs.service.dubbo.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.cs.bean.DefaultResult;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.*;
import com.edianniu.pscp.cs.bean.power.vo.*;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.PowerService;
import com.edianniu.pscp.cs.service.dubbo.PowerInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("powerInfoService")
public class PowerInfoServiceImpl implements PowerInfoService {
	private static final Logger logger = LoggerFactory.getLogger(PowerInfoServiceImpl.class);

	@Autowired
	@Qualifier("powerService")
	private PowerService powerService; 
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	/**
	 * 客户线路列表
	 */
	@Override
	public CompanyLinesResult getCompanyLines(CompanyLinesReqData reqData){
		CompanyLinesResult result = new CompanyLinesResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			
			List<LineVO> lines = powerService.getCompanyLines(companyId);
			result.setLines(lines);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("lines:{}", e);
		}
		return result;
	}
	
	/**
	 * 用电负荷
	 */
	@Override
	public PowerLoadResult getPowerLoad(PowerLoadReqData reqData){
		PowerLoadResult result = new PowerLoadResult();
		try {
			// 验证用户及客户公司信息并返回companyId(此处不可为空)
			Result checkResult1 = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult1.isSuccess()) {
				result.set(checkResult1.getResultCode(), checkResult1.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult1.getObject();
			
			result = powerService.getPowerLoad(companyId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("powerLoad:{}", e);
		}
		return result;
	}
	
	/**
	 * 用电量
	 * @param reqData
	 * @return
	 */
	@Override
	public PowerQuantityResult getPowerQuantity(PowerQuantityReqData reqData){
		PowerQuantityResult result = new PowerQuantityResult();
		try {
			// 验证用户及客户公司信息并返回companyId(此处不可为空)
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			// yyyy-MM
			String date = reqData.getDate();
			if (StringUtils.isNotBlank(date)) {
				if (! BizUtils.checkYearAndMonth(date)) {
					result.set(ResultCode.ERROR_202, "日期格式不正确");
					return result;
				}
				// 判断查询月份是否晚于本月
				String yyyyMM = date.replace("-", "");
				String yyyyMMOfToday = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "").substring(0, 6);
				if (Integer.valueOf(yyyyMM) > Integer.valueOf(yyyyMMOfToday)) {
					result.set(ResultCode.ERROR_202, "查询月份不合法");
					return result;
				}
			}
			result = powerService.getPowerQuantity(companyId, date);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("powerQuantity:{}", e);
		}
		return result;
	}
	
	/**
	 * 用电分布
	 * @param reqData
	 * @return
	 */
	@Override
	public PowerDistributeResult getPowerDistribute(PowerDistributeReqData reqData){
		PowerDistributeResult result = new PowerDistributeResult();
		try {
			// 验证用户及公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			// yyyy-MM
			String date = reqData.getDate();
			if (StringUtils.isNotBlank(date)) {
				if (! BizUtils.checkYearAndMonth(date)) {
					result.set(ResultCode.ERROR_202, "日期格式不正确");
					return result;
				}
				// 判断查询月份是否晚于下月，电费计算按周期计算，比自然月延长一个月
				String yyyyMM = date.replace("-", "");
				String yyyyMMOfToday = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "").substring(0, 6);
				if (Integer.valueOf(yyyyMM) > (Integer.valueOf(yyyyMMOfToday) + 1) ) {
					result.set(ResultCode.ERROR_202, "查询月份不合法");
					return result;
				}
			}
			result = powerService.getPowerDistribute(companyId, date);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("PowerDistribute:{}", e);
		}
		
		return result;
	}
	
	/**
	 * 功率因数
	 * @param reqData
	 * @return
	 */
	@Override
	public PowerFactorResult getPowerFactor(PowerFactorReqData reqData){
		PowerFactorResult result = new PowerFactorResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Long lineId = reqData.getLineId();
			if (null != lineId) {
				Boolean isExist = powerService.isExistLine(companyId, lineId);
				if (! isExist) {
					result.set(ResultCode.ERROR_201, "该线路不属于该用户");
					return result;
				}
			}
			
			result = powerService.getPowerFactor(companyId, lineId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("PowerFactor:{}", e);
		}
		return result;
	}
	
	/**
	 * 电费单
	 * @param reqData
	 * @return
	 */
	@Override
	public ChargeBillResult getChargeBill(ChargeBillReqData reqData){
		ChargeBillResult result = new ChargeBillResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			//yyyy-MM
			String date = reqData.getDate();
			if (StringUtils.isNotBlank(date)) {
				if (! BizUtils.checkYearAndMonth(date)) {
					result.set(ResultCode.ERROR_202, "日期格式不正确");
					return result;
				}
				// 判断查询月份是否晚于下月，电费计算按周期计算，比自然月延长一个月
				String yyyyMM = date.replace("-", "");
				String yyyyMMOfToday = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "").substring(0, 6);
				if (Integer.valueOf(yyyyMM) > (Integer.valueOf(yyyyMMOfToday) + 1) ) {
					result.set(ResultCode.ERROR_202, "查询月份不合法");
					return result;
				}
			}
			result = powerService.getChargeBill(companyId, date);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("ChargeBill:{}", e);
		}
		return result;
	}
	
	/**
	 * 电费明细
	 * @param reqData
	 * @return
	 */
	@Override
	public ChargeDetailResult getChargeDetail(ChargeDetailReqData reqData){
		ChargeDetailResult result = new ChargeDetailResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			//yyyy-MM
			String date = reqData.getDate();
			if (StringUtils.isNotBlank(date)) {
				if (! BizUtils.checkYearAndMonth(date)) {
					result.set(ResultCode.ERROR_202, "日期格式不正确");
					return result;
				}
				// 判断查询月份是否晚于下月，电费计算按周期计算，比自然月延长一个月
				String yyyyMM = date.replace("-", "");
				String yyyyMMOfToday = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "").substring(0, 6);
				if (Integer.valueOf(yyyyMM) > (Integer.valueOf(yyyyMMOfToday) + 1) ) {
					result.set(ResultCode.ERROR_202, "查询月份不合法");
					return result;
				}
			}
			result = powerService.getChargeDetail(companyId, date);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("ChargeDetail:{}", e);
		}
		return result;
	}
	
	/**
	 * 客户设备告警新增(测试时使用)
	 * 废弃--20180307 by zjj
	 */
	@Override
	public WarningSaveResult saveWarning(WarningSaveReqData reqData){
		WarningSaveResult result = new WarningSaveResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			if (! BizUtils.checkLength(reqData.getWarningType(), 200)) {
				result.set(ResultCode.ERROR_401, "告警类型不能为空或超过200字");
				return result;
			}
			if (null != reqData.getWarningObject()) {
				if (! BizUtils.checkLength(reqData.getWarningObject(), 200)) {
					result.set(ResultCode.ERROR_401, "告警对象不能超过200字");
					return result;
				}
			}
			if (null == DateUtils.parse(reqData.getOccurTime(), DateUtils.DATE_TIME_PATTERN)) {
				result.set(ResultCode.ERROR_401, "发生时间不合法");
				return result;
			}
			if (null != reqData.getDescription()) {
				if (! BizUtils.checkLength(reqData.getDescription(), 1000)) {
					result.set(ResultCode.ERROR_401, "告警描述不能超过200字");
					return result;
				}
			}
			powerService.saveWarning(reqData, companyId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("saveWarning:{}", e);
		}
		return result;
	}
	
	/**
	 * 报警列表
	 */
	@Override
	public WarningListResult getWarningList(WarningListReqData reqData){
		WarningListResult result = new WarningListResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Integer offset = reqData.getOffset();
			Integer pageSize = reqData.getPageSize();
			PageResult<WarningVO> pageResult = powerService.getWarningList(companyId, offset, pageSize);
			
			result.setWarnings(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("WarningList:{}", e);
		}
		return result;
	}
	
	/**
	 * 报警详情
	 */
	@Override
	public WarningDetailResult getWarningDetail(WarningDetailReqData reqData){
		WarningDetailResult result = new WarningDetailResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long id = reqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_202, "id不能为空");
				return result;
			}
			
			WarningVO warning = powerService.getWarningDetail(id);
			result.setWarning(warning);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("WarningDetail:{}", e);
		}
		return result;
	}
	
	
	/**
	 * 验证用户信息和客户公司信息，并返回companyId
	 * @param uid
	 * @return
	 */
	public Result checkUserAndCustomerCompanyInfo(Long uid){
		Result result = new DefaultResult();
		if (null == uid) {
			result.set(ResultCode.ERROR_201, "uid不能为空");
			return result;
		}
		GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
		if (!getUserInfoResult.isSuccess()) {
			result.set(ResultCode.ERROR_201, "用户信息不存在");
			return result;
		}
		UserInfo userInfo = getUserInfoResult.getMemberInfo();
		CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
		if (null == companyInfo) {
			result.set(ResultCode.ERROR_201, "用户公司信息不存在");
			return result;
		}
		if (! userInfo.isCustomer()) {
			result.set(ResultCode.ERROR_201, "要查询的公司未认证为客户");
			return result;
		}
		Long companyId = userInfo.getCompanyId();
		if (null == companyId) {
			result.set(ResultCode.ERROR_201, "用户公司信息不存在");
			return result;
		}
		result.setObject(companyId);
		return result;
	}
	
	/**
	 * 验证用户信息和服务商公司信息，并返回companyId
	 * @param uid
	 * @return
	 */
	public Result checkUserAndFacilitatorCompanyInfo(Long uid){
		Result result = new DefaultResult();
		if (null == uid) {
			result.set(ResultCode.ERROR_201, "uid不能为空");
			return result;
		}
		GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
		if (!getUserInfoResult.isSuccess()) {
			result.set(ResultCode.ERROR_201, "用户信息不存在");
			return result;
		}
		UserInfo userInfo = getUserInfoResult.getMemberInfo();
		CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
		if (null == companyInfo) {
			result.set(ResultCode.ERROR_201, "用户公司信息不存在");
			return result;
		}
		if (! userInfo.isFacilitator()) {
			result.set(ResultCode.ERROR_201, "要查询的公司未认证为服务商");
			return result;
		}
		Long companyId = userInfo.getCompanyId();
		if (null == companyId) {
			result.set(ResultCode.ERROR_201, "用户公司信息不存在");
			return result;
		}
		result.setObject(companyId);
		return result;
	}
	
	/**
	 * 门户：智能监控>监控列表
	 * @param reqData
	 * @return
	 */
	@Override
	public MonitorListResult getmonitorList(MonitorListReqData reqData){
		MonitorListResult result = new MonitorListResult();
		try {
			// 验证用户及服务商公司信息并返回companyId
			Result checkResult = checkUserAndFacilitatorCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Integer limit = reqData.getLimit() == null ? 10 : reqData.getLimit();
			Integer offset = reqData.getOffset() == null ? 0 : reqData.getOffset();
			
			PageResult<MonitorVO> pageResult = powerService.getMonitorList(companyId, limit, offset);
			result.setMonitorList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("MonitorList:{}", e);
		}
		return result;
	}
	
	/**
	 * 门户：智能监控>监控列表>总览>总览-实时负荷
	 * @param reqData
	 * @return
	 */
	@Override
	public RealTimeLoadResult getRealTimeLoad(RealTimeLoadReqData reqData){
		RealTimeLoadResult result = new RealTimeLoadResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			String date = reqData.getDate();
			// date 精确到日
			if (StringUtils.isNotBlank(date)) {
				if (null == DateUtils.parse(date)) {
					result.set(ResultCode.ERROR_201, "日期格式不合法");
					return result;
				}
				// 判断查询日期是否晚于今日
				String yyyyMMdd = date.replace("-", "");
				String yyyyMMddOfToday = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "");
				if (Integer.valueOf(yyyyMMdd) > Integer.valueOf(yyyyMMddOfToday) ) {
					result.set(ResultCode.ERROR_202, "查询日期不合法");
					return result;
				}
			}
			result = powerService.getRealTimeLoad(companyId, date);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("RealTimeLoad:{}", e);
		}
		return result;		
	}
	
	/**
	 * 门户：智能监控>监控列表>总览>（综合、动力、照明、空调、特殊）
	 * @param reqData
	 * @return
	 */
	@Override
	public GeneralByTypeResult getGeneralByType(GeneralByTypeReqData reqData){
		GeneralByTypeResult result = new GeneralByTypeResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			String date = reqData.getDate();
			// date精确到日
			if (StringUtils.isNotBlank(date)) {
				if (null == DateUtils.parse(date)) {
					result.set(ResultCode.ERROR_201, "日期格式不合法");
					return result;
				}
				// 判断查询日期是否晚于今日
				String yyyyMMdd = date.replace("-", "");
				String yyyyMMddOfToday = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "");
				if (Integer.valueOf(yyyyMMdd) > Integer.valueOf(yyyyMMddOfToday) ) {
					result.set(ResultCode.ERROR_202, "查询日期不合法");
					return result;
				}
			}
			Integer type = reqData.getType();
			if (null == type) {
				result.set(ResultCode.ERROR_202, "type不能为空");
				return result;
			}
			Boolean exist = EquipmentType.isExist(type);
			if (! exist) {
				result.set(ResultCode.ERROR_202, "type不合法");
				return result;
			}
			Integer limit = reqData.getLimit() == null ? 10 : reqData.getLimit();
			Integer offset = reqData.getOffset() == null ? 0 : reqData.getOffset();
			
			PageResult<CollectorPointVO> pageResult = powerService.getGeneralByType(companyId, type, date, limit, offset);
			result.setCollectorPoints(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("GeneralByType:{}", e);
		}
		return result;
	}
	
	/**
	 * 门户：智能监控>告警
	 * 获取服务商所有客户的告警列表
	 * @author zhoujianjian
	 * @date 2017年12月14日 下午3:05:33
	 */
	@Override
	public WarningListResult getAllWarnings(AllWarningsReqData reqData){
		WarningListResult result = new WarningListResult();
		try {
			// 验证用户及服务商公司信息并返回companyId
			Result checkResult = checkUserAndFacilitatorCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Integer limit = reqData.getLimit() == null ? 10 : reqData.getLimit();
			Integer offset = reqData.getOffset() == null ? 0 : reqData.getOffset();
			
			PageResult<WarningVO> pageResult = powerService.getAllWarnings(companyId, limit, offset);
			result.setWarnings(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("WarningList:{}", e);
		}
		return result;
	}
	
	/**
	 * 门户：智能监控>监控列表>用能排行
	 * @param reqData
	 * @return
	 */
	@Override
	public ConsumeRankResult getConsumeRank(ConsumeRankReqData reqData){
		ConsumeRankResult result = new ConsumeRankResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Integer offset = reqData.getOffset() == null ? 0 : reqData.getOffset();
			Integer limit = reqData.getLimit() == null ? 10 : reqData.getLimit();
			//yyyy-MM
			String date = reqData.getDate();
			if (StringUtils.isNotBlank(date)) {
				if (! BizUtils.checkYearAndMonth(date)) {
					result.set(ResultCode.ERROR_202, "日期格式不正确");
					return result;
				}
				// 判断查询月份是否晚于下月，电费计算按周期计算，比自然月延长一个月
				String yyyyMM = date.replace("-", "");
				String yyyyMMOfToday = DateUtils.format(new Date(), DateUtils.DATE_PATTERN).replace("-", "").substring(0, 6);
				if (Integer.valueOf(yyyyMM) > (Integer.valueOf(yyyyMMOfToday) + 1) ) {
					result.set(ResultCode.ERROR_202, "查询月份不合法");
					return result;
				}
			}
			PageResult<BuildingVO> pageResult = powerService.getConsumeRank(companyId, date, limit, offset);
			result.setBuildings(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("ConsumeRank:{}", e);
		}
		
		return result;
	}
	
	/**
	 * 门户：智能监控>监控列表>电压健康
	 * @param reqData
	 * @return
	 */
	@Override
	public SafetyVoltageResult getSafetyVoltage(SafetyVoltageReqData reqData){
		SafetyVoltageResult result = new SafetyVoltageResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			
			result = powerService.getSafetyVoltage(companyId);
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("SafetyVoltage:{}", e);
		}
		return result;
	}
	
	/**
	 * 门户：智能监控>监控列表>电流平衡
	 * @param reqData
	 * @return
	 */
	@Override
	public CurrentBalanceResult getCurrentBalance(CurrentBalanceReqData reqData){
		CurrentBalanceResult result = new CurrentBalanceResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Long lineId = reqData.getLineId();
			if (null != lineId) {
				Boolean isExit = powerService.isExistLine(companyId, lineId);
				if (!isExit) {
					result.set(ResultCode.ERROR_202, "该客户不存在该线路");
					return result;
				}
			}
			result = powerService.getCurrentBalance(companyId, lineId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("CurrentBalance:{}", e);
		}
		return result;
	}
	
	/**
	 * 分页获取监测点数据
	 * 门户：功率因数---获取监测点功率因数
	 */
	@Override
	public CollectorPointsResult getCollectorPointsForPowerFactor(CollectorPointsReqData reqData){
		CollectorPointsResult result = new CollectorPointsResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Integer offset = reqData.getOffset() == null ? 0 : reqData.getOffset();
			Integer limit = reqData.getLimit() == null ? 10 : reqData.getLimit();
			
			PageResult<CollectorPointVO> pageResult = powerService.getCollectorPointsForPowerFactor(companyId, limit, offset);
			result.setCollectorPoints(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("CollectorPoints:{}", e);
		}
		return result;
	}
	
	/**
	 * 分页获取监测点数据
	 * 门户：峰谷利用---获取监测点在监测时段用电量、电费
	 */
	@Override
	public CollectorPointsResult getCollectorPointsForPowerQuantity(CollectorPointsReqData reqData){
		CollectorPointsResult result = new CollectorPointsResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Integer offset = reqData.getOffset() == null ? 0 : reqData.getOffset();
			Integer limit = reqData.getLimit() == null ? 10 : reqData.getLimit();
			
			String startTime = reqData.getStartTime();
			String endTime = reqData.getEndTime();
			boolean isTrue1 = BizUtils.cheackTimePattern(startTime);
			boolean isTrue2 = BizUtils.cheackTimePattern(endTime);
			if (! (isTrue1 && isTrue2)) {
				result.set(ResultCode.ERROR_201, "时间格式不正确");
				return result;
			}
			
			PageResult<CollectorPointVO> pageResult = powerService.getCollectorPointsForPowerQuantity(companyId, limit, offset, startTime, endTime);
			result.setCollectorPoints(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("CollectorPoints:{}", e);
		}
		return result;
	}
	
	/**
	 * 门户：智能监控>监控列表>峰谷利用
	 * @param reqData
	 * @return
	 */
	@Override
	public UseFengGUResult useFengGU(UseFengGUReqData reqData){
		UseFengGUResult result = new UseFengGUResult();
		try {
			// 验证用户及客户公司信息并返回companyId
			Result checkResult = checkUserAndCustomerCompanyInfo(reqData.getUid());
			if (!checkResult.isSuccess()) {
				result.set(checkResult.getResultCode(), checkResult.getResultMessage());
				return result;
			}
			Long companyId = (Long) checkResult.getObject();
			Long lineId = reqData.getLineId();
			if (null != lineId) {
				Boolean isExit = powerService.isExistLine(companyId, lineId);
				if (!isExit) {
					result.set(ResultCode.ERROR_202, "该客户不存在该线路");
					return result;
				}
			}
			
			result = powerService.useFengGU(companyId, lineId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("UseFengGU:{}", e);
		}
		return result;
	}
	
	/**
	 * 判断采集点是否有数据
	 * @param meterId    采集点编号
	 * @param companyId  客户公司ID
	 * @return
	 */
	@Override
	public HasDataResult hasData(String meterId, Long companyId){
		HasDataResult result = new HasDataResult();
		try {
			if (StringUtils.isBlank(meterId)) {
				result.set(ResultCode.ERROR_201, "meterId不能为空");
				return result;
			}
			if (null == companyId) {
				result.set(ResultCode.ERROR_201, "客户公司ID不能为空");
				return result;
			}
			// 判断该仪表是否属于该客户
			Boolean existMeter = powerService.isExistMeter(companyId, meterId);
			if (! existMeter) {
				result.set(ResultCode.ERROR_201, "客户不存在编号为" + meterId + "的仪表");
				return result;
			}
			result = powerService.hasData(meterId, companyId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("hasData:{}", e);
		}
		return result;
	}
	
}
