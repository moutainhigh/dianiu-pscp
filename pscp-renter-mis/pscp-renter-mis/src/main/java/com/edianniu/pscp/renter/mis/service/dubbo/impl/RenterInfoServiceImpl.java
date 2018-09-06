package com.edianniu.pscp.renter.mis.service.dubbo.impl;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.edianniu.pscp.renter.mis.domain.PowerPriceConfig;
import com.edianniu.pscp.renter.mis.domain.Renter;
import com.edianniu.pscp.renter.mis.domain.RenterConfig;
import com.edianniu.pscp.renter.mis.domain.RenterMeter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.CreateUserResult;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.request.NetDauControlRequest;
import com.edianniu.pscp.mis.response.NetDauControlResponse;
import com.edianniu.pscp.mis.service.NetDauControlService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.renter.mis.bean.DefaultResult;
import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.*;
import com.edianniu.pscp.renter.mis.bean.renter.vo.*;
import com.edianniu.pscp.renter.mis.commons.CacheKey;
import com.edianniu.pscp.renter.mis.commons.PageResult;
import com.edianniu.pscp.renter.mis.commons.ResultCode;
import com.edianniu.pscp.renter.mis.service.RenterMeterService;
import com.edianniu.pscp.renter.mis.service.RenterOrderService;
import com.edianniu.pscp.renter.mis.service.RenterService;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterInfoService;
import com.edianniu.pscp.renter.mis.util.BizUtils;
import com.edianniu.pscp.renter.mis.util.DateUtils;
import stc.skymobi.cache.redis.JedisUtil;

@Service
@Repository("renterInfoService")
public class RenterInfoServiceImpl implements RenterInfoService {

private static final Logger logger = LoggerFactory.getLogger(RenterInfoService.class);
	
	@Autowired
	@Qualifier("renterService")
	private RenterService renterService;
	
	@Autowired
	@Qualifier("renterOrderService")
	private RenterOrderService renterOrderService;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("renterMeterService")
	private RenterMeterService renterMeterService;
	@Autowired
	@Qualifier("netDauControlService")
	private NetDauControlService netDauControlService;
	
	@Autowired
	@Qualifier("messageInfoService")
	private MessageInfoService messageInfoService;
	
	@Autowired
	@Qualifier("jedisUtil")
	private JedisUtil jedisUtil;
	
	public static final String HOME_RENTER_ID_CACHE = "home_renter_id_cache#";
	@Override
	public List<Long> getPrePaymentRenterIds(int limit) {
		
		return renterService.getRenterIds(ChargeModeType.PAY_FIRST.getValue(), limit);
	}

	@Override
	public List<Long> getMonthlyPaymentRenterIds(int limit) {
		
		return renterService.getRenterIds(ChargeModeType.MONTH_SETTLE.getValue(), limit);
	}
	
	/**
	 * 租客列表
	 */
	@Override
	public ListResult renterList(ListReq req){
		ListResult result = new ListResult();
		try {
			Long uid = req.getUid();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "客户uid不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			UserInfo userInfo = userInfoResult.getMemberInfo();
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			Date sdate = null;
			Date bdate = null;
			if (null != req.getSdate() && req.getSdate().trim().length()!=0) {
				sdate = DateUtils.parse(req.getSdate(), DateUtils.DATE_PATTERN);
				if (null == sdate) {
					result.set(ResultCode.ERROR_201, "日期输入不合法");
					return result;
				}
			}
			if (null != req.getBdate() && req.getBdate().trim().length()!=0) {
				bdate = DateUtils.parse(req.getBdate(), DateUtils.DATE_PATTERN);
				if (null == bdate) {
					result.set(ResultCode.ERROR_201, "日期输入不合法");
					return result;
				}
			}
			if (null != sdate && null != bdate ) {
				if (sdate.after(bdate)) {
					result.set(ResultCode.ERROR_201, "日期输入不合法");
					return result;
				}
			}
			HashMap<String, Object> queryMap = new HashMap<>();
			queryMap.put("companyId", companyId);
			queryMap.put("type", req.getType());
			queryMap.put("status", req.getStatus());
			queryMap.put("offset", req.getOffset() == null ? 0 : req.getOffset());
			queryMap.put("limit", req.getLimit() == null ? 10 : req.getLimit());
			queryMap.put("mobile", req.getMobile() == null ? null : req.getMobile().trim());
			queryMap.put("name", req.getName() == null ? null : req.getName().trim());
			queryMap.put("sdate", sdate == null ? null : DateUtils.getStartDate(sdate));
			queryMap.put("bdate", bdate == null ? null : DateUtils.getEndDate(bdate));
			
			PageResult<RenterVO> pageResult = renterService.getList(queryMap);
			result.setRenterList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renter list:{}", e);
		}
		return result;
	}
	
	/**
	 * 租客仪表
	 */
	@Override
	public RenterMetersResult getMeters(RenterMetersReq req){
		RenterMetersResult result = new RenterMetersResult();
		try {
			Long uid = req.getUid();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "客户uid不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			UserInfo userInfo = userInfoResult.getMemberInfo();
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			Long companyId = userInfo.getCompanyId();
			
			Long renterId = req.getRenterId();
			Renter renter = renterService.getById(renterId);
			if (null == renterId || null == renter) {
				result.set(ResultCode.ERROR_201, "renterId不合法");
				return result;
			}
			if (!companyId.equals(renter.getCompanyId())) {
				result.set(ResultCode.ERROR_201, "renterId不合法");
				return result;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("renterId", req.getRenterId());
			map.put("limit", req.getLimit());
			map.put("offset", req.getOffset() == null ? 0 : req.getOffset());
			PageResult<RenterMeterInfo> pageResult = renterMeterService.getPageMeters(map);
			result.setRenterMeters(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setTotalCount(pageResult.getTotal());
			result.setNextOffset(pageResult.getNextOffset());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renter meter list:{}", e);
		}
		return result;
	}
	
	
	/**
	 * 租客用电数据列表
	 */
	@Override
	public DataListResult getDataList(DataListReq req){
		DataListResult result = new DataListResult();
		try {
			Long uid = req.getUid();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "客户uid不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			UserInfo userInfo = userInfoResult.getMemberInfo();
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			String time = req.getTime();
			if (StringUtils.isBlank(time)) { 
				time = null;   // 默认按月份查询，查询当前月，在service层处理
			} else {
				if (!BizUtils.checkYMD(time)) {
					result.set(ResultCode.ERROR_201, "查询日期不合法");
					return result;
				}
			}
			HashMap<String, Object> queryMap = new HashMap<>();
			queryMap.put("companyId", companyId);
			queryMap.put("offset", req.getOffset() == null ? 0 : req.getOffset());
			queryMap.put("limit", req.getLimit() == null ? 10 : req.getLimit());
			PageResult<RenterDataVO> pageResult = renterService.getDataList(queryMap, companyId, time);
			
			result.setDataList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renter data list:{}", e);
		}
		return result;
	}
	
	/**
	 * 租客账单列表
	 */
	@Override
	public OrderListResult getOrderList(OrderListReq req){
		OrderListResult result = new OrderListResult();
		try {
			Long uid = req.getUid();
			Long renterId = req.getRenterId();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "租客uid不能为空");
				return result;
			}
			if (null == renterId) {
				result.set(ResultCode.ERROR_201, "租客ID均不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess() || null == userInfoResult.getMemberInfo()) {
				result.set(ResultCode.ERROR_201, "租客uid不合法");
				return result;
			}
			RenterVO renterVO = renterService.getById(renterId, null);
			if (null == renterVO) {
				result.set(ResultCode.ERROR_201, "租客ID不合法");
				return result;
			}
			// 余额
			String balance = renterVO.getBalance();
			
			Map<String, Object> map = new HashMap<>();
			map.put("renterId", renterId);
			map.put("companyId", renterVO.getCompanyId());
			map.put("payStatus", req.getPayStatus());
			map.put("limit", req.getPageSize() == null ? 10 : req.getPageSize());
			map.put("offset", req.getOffset() == null ? 0 : req.getOffset());
			
			result = renterOrderService.getOrderList(map, renterVO.getCompanyId());
			result.setBalance(balance);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renter order list:{}", e);
		}
		return result;
	}
	
	/**
	 * 租客账单详情
	 */
	@Override
	public OrderDetailResult getOrderDetail(OrderDetailReq req){
		OrderDetailResult result = new OrderDetailResult();
		try {
			Long uid = req.getUid();
			Long id = req.getId();
			if (null == uid || null == id) {
				result.set(ResultCode.ERROR_201, "租客uid和账单ID不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess() || null == userInfoResult.getMemberInfo()) {
				result.set(ResultCode.ERROR_201, "租客uid不合法");
				return result;
			}
			result = renterOrderService.getOrderDetail(id);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renter order detail:{}", e);
		}
		return result;
	}
	
	/**
	 * 租客-房东关系对
	 * @param uid   租客uid
	 * @return
	 */
	@Override
	public RentersResult getRenters(Long uid){
		RentersResult result = new RentersResult();
		try {
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "租客uid不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess() || null == userInfoResult.getMemberInfo()) {
				result.set(ResultCode.ERROR_201, "租客uid不合法");
				return result;
			}
			List<RenterVO> volist = renterService.getList(uid);
			if (CollectionUtils.isEmpty(volist)) {
				result.set(ResultCode.NO_RENTER_COOPERATION, "您尚未与任何公司合作");
				return result;
			}
			result.setRenters(volist);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renters:{}", e);
		}
		return result;
	}
	
	/**
	 * 租客首页
	 */
	@Override
	public HomeResult home(HomeReq req){
		HomeResult result = new HomeResult();
		try {
			Long uid = req.getUid();
			Long renterId = req.getRenterId();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "租客uid不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess() || null == userInfoResult.getMemberInfo()) {
				result.set(ResultCode.ERROR_201, "租客uid不合法");
				return result;
			}
			List<RenterVO> volist = renterService.getList(uid);
			if (CollectionUtils.isEmpty(volist)) {
				result.set(ResultCode.NO_RENTER_COOPERATION, "您尚未与任何公司合作");
				return result;
			}
			/*renterId验证*/
			if (null != renterId) { //renterId不为空，将renterId信息保存到缓存，供下次登录默认使用
				jedisUtil.set(HOME_RENTER_ID_CACHE + uid, String.valueOf(renterId));
			} else { //renterId为空，先从缓存查找上次是否留有记录，如果没有记录则默认选择最先合作的租客关系
				String renterId_String = jedisUtil.get(HOME_RENTER_ID_CACHE + uid);
				if (StringUtils.isNotBlank(renterId_String)) { //缓存有记录
					renterId = Long.valueOf(renterId_String);
				} else { //缓存没有记录，获取最先合作的租客关系，并保存至缓存
					renterId = volist.get(0).getRenterId();
					jedisUtil.set(HOME_RENTER_ID_CACHE + uid, String.valueOf(renterId));
				}
			}
			RenterVO renterVO = renterService.getById(renterId, null);
			if (null == renterVO) {
				result.set(ResultCode.ERROR_201, "租客ID不合法");
				return result;
			}
			
			result = renterService.home(renterId, renterVO.getCompanyId(), renterVO.getBalance());
			result.setRenters(volist);
			result.setRenterId(renterVO.getRenterId());
			result.setRenterName(renterVO.getName());
			result.setCompanyId(renterVO.getCompanyId());
			result.setCompanyName(renterVO.getCompanyName());
			result.setBalance(renterVO.getBalance());// 余额
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renter home:{}", e);
		}
		return result;
	}
	
	@Override
	public Boolean isUserExist(String mobile){
		return userInfoService.isUserExist(mobile.trim());
	}
	
	@Override
	public Boolean isRenterNameExist(String name, Long companyId){
		return renterService.isRenterNameExist(name.trim(), companyId);
	}
	
	@Override
	public Boolean isRenterExit(String mobile, Long companyId){
		return renterService.isRenterExit(mobile.trim(), companyId);
	}
	
	/**
	 * 1.租客id为空时，新增租客；反之，编辑租客。
	 * 2.新增时需验证手机号码之前是否注册过。若已注册则不再次添加用户，且不再重设密码；若之前未注册，则注册为普通用户。pscp_member表
	 * 3.新增一个租客-客户关系对，在此之前还需判断这种租客关系是否早已存在。pscp_company_renter表
	 */
	@Override
	public SaveResult saveRenter(SaveReq req){
		SaveResult result = new SaveResult();
		try {
			Long uid = req.getUid();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "客户uid不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			UserInfo userInfo = userInfoResult.getMemberInfo();
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			if (! BizUtils.checkLength(req.getName(), 32)) {
				result.set(ResultCode.ERROR_201, "租客名称不得为空或超过32个字");
				return result;
			}
			if (! BizUtils.checkMobile(req.getMobile())) {
				result.set(ResultCode.ERROR_201, "手机号码不合法");
				return result;
			}
			if (! BizUtils.checkLength(req.getContract(), 32)) {
				result.set(ResultCode.ERROR_201, "联系人不得为空或超过32个字");
				return result;
			}
			if (StringUtils.isNotBlank(req.getAddress())) {
				if (!BizUtils.checkLength(req.getAddress(), 100)) {
					result.set(ResultCode.ERROR_201, "地址不得超过100个字");
					return result;
				}
			}
			Long renterId = req.getId();
			if (null == renterId) { /**新增租客**/
				// 用户表操作
				Long renterMemberId = null;
				if (isUserExist(req.getMobile())) { //如果用户之前已经注册，则不需要重复添加用户
					GetUserInfoResult renterUserInfoResult = userInfoService.getUserInfoByMobile(req.getMobile());
					if (!renterUserInfoResult.isSuccess()) {
						result.set(renterUserInfoResult.getResultCode(), renterUserInfoResult.getResultMessage());
						return result;
					}
					UserInfo renterUserInfo = renterUserInfoResult.getMemberInfo();
					renterMemberId = renterUserInfo.getUid();
				} else {                            //如果用户之前未曾注册，则需要添加用户（普通用户）
					if (!BizUtils.checkLength(req.getPwd(), 64)) {
						result.set(ResultCode.ERROR_201, "密码不得为空或超过64个字");
						return result;
					}
					CreateUserResult createUserResult = userInfoService.createUser(req.getMobile(), req.getPwd());
					if (!createUserResult.isSuccess()) {
						result.set(createUserResult.getResultCode(), createUserResult.getResultMessage());
						return result;
					}
					renterMemberId = createUserResult.getUid();
				}
				// 租客表操作
				if (isRenterExit(req.getMobile(), companyId)) {
					result.set(ResultCode.ERROR_201, req.getMobile() + "的用户已经是您的租客，请勿重复添加");
					return result;
				}
				if (isRenterNameExist(req.getName(), companyId)) {
					result.set(ResultCode.ERROR_201, "租客名称重复");
					return result;
				}
				long r = jedisUtil.setnx(CacheKey.CACHE_KEY_RENTER_USER_MOBILE + req.getMobile(), req.getMobile(), 1000L);
				if (r == 0) {
					result.set(ResultCode.ERROR_201, "操作进行中，请勿重复操作");
					return result;
				}
				renterId = renterService.save(req.getName(), req.getMobile(), req.getContract(), req.getAddress(), renterMemberId, companyId);
			} else { /**编辑租客**/
				// 租客表操作
				RenterVO renterVO = renterService.getById(req.getId(), companyId);
				if (null == renterVO) {
					result.set(ResultCode.ERROR_201, "租客ID不合法");
					return result;
				}
				long r = jedisUtil.setnx(CacheKey.CACHE_KEY_RENTER_USER_MOBILE + req.getMobile(), req.getMobile(), 1000L);
				if (r == 0) {
					result.set(ResultCode.ERROR_201, "操作进行中，请勿重复操作");
					return result;
				}
				renterService.update(renterId, req.getName(), req.getMobile(), req.getContract(), req.getAddress());
			}
			result.setRenterId(renterId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("save renter:{}", e);
		}
		return result;
	}
	
	
	@Override
	public DetailResult getRenter(DetailReq req){
		DetailResult result = new DetailResult();
		try {
			if (null == req.getUid() || null == req.getRenterId()) {
				result.set(ResultCode.ERROR_201, "客户uid和租客ID均不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(req.getUid());
			if (!userInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			UserInfo userInfo = userInfoResult.getMemberInfo();
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			RenterVO renterVO = renterService.getById(req.getRenterId(), companyId);
			if (null == renterVO) {
				result.set(ResultCode.ERROR_201, "租客ID不合法");
				return result;
			}
			result.setRenterVO(renterVO);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
		}
		return result;
	}
	
	/**
	 * 4.保存租客配置。pscp_renter_config表
	 * 5.保存租客采集点配置。pscp_renter_meter表
	 * 6.修改仪表费用占比状态。pscp_company_meter表
	 * 备注：只有费用占比总和小于100的仪表才会出现在被选择的列表，即allot系数为0的仪表
	 */
	@Override
	public SaveResult saveRenterConfig(SaveConfigReq req){
		SaveResult result = new SaveResult();
		try {
			Long renterId = req.getRenterId();
			if (null == renterId) {
				result.set(ResultCode.ERROR_201, "租客id不能为空");
				return result;
			}
			RenterVO renterVO = renterService.getById(renterId, null);
			if (null == renterVO) {
				result.set(ResultCode.ERROR_201, "租客id不合法");
				return result;
			}
			/*计费方式*/   
			Long companyId = renterVO.getCompanyId();
			PowerPriceConfig config = renterOrderService.getPowerPriceConfig(companyId);
			if (null == config) {
				result.set(ResultCode.ERROR_201, "请先配置用电参数");
				return result;
			}
			if (!config.getChargeMode().equals(req.getSubChargeMode())) {
				result.set(ResultCode.ERROR_201, "计费方式不合法");
				return result;
			}
			if (req.getSubChargeMode().equals(RenterConfig.NORMAL_CHARGE_MODE)) {
				/* 普通（统一单价） */
				if (!BizUtils.isPositiveNumber(req.getBasePrice())) {
					result.set(ResultCode.ERROR_201, "单价不合法");
					return result;
				}
			} else {
				/* 分时 */
				if (StringUtils.isBlank(req.getApexPrice()) && 
						StringUtils.isBlank(req.getPeakPrice()) && 
						StringUtils.isBlank(req.getFlatPrice()) && 
						StringUtils.isBlank(req.getValleyPrice()) ) {
					result.set(ResultCode.ERROR_201, "单价不能全为空");
					return result;
				}
				if (!StringUtils.isBlank(req.getApexPrice())) {
					if (!BizUtils.isPositiveNumber(req.getApexPrice())) {
						result.set(ResultCode.ERROR_201, "尖单价不合法");
						return result;
					}
				}
				if (!StringUtils.isBlank(req.getPeakPrice()) ) {
					if (!BizUtils.isPositiveNumber(req.getPeakPrice())) {
						result.set(ResultCode.ERROR_201, "峰单价不合法");
						return result;
					}
				}
				if (!StringUtils.isBlank(req.getFlatPrice())) {
					if (!BizUtils.isPositiveNumber(req.getFlatPrice())) {
						result.set(ResultCode.ERROR_201, "平单价不合法");
						return result;
					}
				}
				if (!StringUtils.isBlank(req.getValleyPrice())) {
					if (!BizUtils.isPositiveNumber(req.getValleyPrice())) {
						result.set(ResultCode.ERROR_201, "谷单价不合法");
						return result;
					}
				}
			}
			/* 缴费方式 */
			if (!ChargeModeType.isValueExist(req.getChargeMode())) {
				result.set(ResultCode.ERROR_201, "缴费方式不合法");
				return result;
			}
			if (req.getChargeMode().equals(ChargeModeType.PAY_FIRST.getValue())) {
				if (!BizUtils.isPositiveNumber(req.getAmountLimit())) {
					result.set(ResultCode.ERROR_201, "预缴费模式需要设置余额提醒值");
					return result;
				}
			}
			Date startTime = DateUtils.parse(req.getStartTime());
			if (null == startTime) {
				result.set(ResultCode.ERROR_201, "计费开始日期不合法");
				return result;
			}
			Date endTime = DateUtils.parse(req.getEndTime());
			if (endTime != null) {
				if (endTime.before(startTime)) {
					result.set(ResultCode.ERROR_201, "计费结束日期不合法");
					return result;
				}
			}
			/*仪表信息验证*/
			List<RenterMeterInfo> meterList = req.getMeterList();
			if (CollectionUtils.isEmpty(meterList)) {
				result.set(ResultCode.ERROR_201, "请选择设备要绑定的仪表");
				return result;
			}
			Set<Long> meterIdSet = new HashSet<>(); 
			for (RenterMeterInfo meter : meterList) {
				if (null == meter.getId() || null == meter.getName() || 
					null == meter.getBuildingId() || null == meter.getRate() ) {
					result.set(ResultCode.ERROR_201, "参数不合法");
					return result;
				}
				double rateSum = renterService.rateSum(meter.getId());
				double oldRate = renterService.getRate(meter.getId(), renterId);
				// 查询该仪表自己之前占有多大的比例
				if (rateSum - oldRate + meter.getRate() > 100) {
					result.set(ResultCode.ERROR_201, meter.getName() + "费用占比总和已超过100%，请重新设置");
					return result;
				}
				meterIdSet.add(meter.getId());
			}
			// 验证仪表id是否重复
			if (meterList.size() != meterIdSet.size()) {
				result.set(ResultCode.ERROR_201, "请勿重复选择同一仪表");
				return result;
			}
			Long configId = req.getId();
			if (null == configId) { //新建配置
				if (isConfigExist(null, renterId)) {
					result.set(ResultCode.ERROR_201, "ID为" + renterId + "的租客配置已存在，不可重复新增");
					return result;
				}
				/*初始化首次账单生成日期*/
				Date firstOrderTime = null;
				if (req.getChargeMode().equals(ChargeModeType.PAY_FIRST.getValue())) {
				//1.预缴费租客，首次账单生成时间为：租客入网日（开始计费日）00:00:0
					firstOrderTime = DateUtils.getStartDate(startTime);
				} else {
				//2.月结算租客，首次账单生成日应分别处理
					int joinDate = Integer.valueOf(req.getStartTime().substring(8, 10));
					int joinMonth = Integer.valueOf(req.getStartTime().substring(5, 7));
					int joinYear = Integer.valueOf(req.getStartTime().substring(0, 4));
					int countDate = Integer.valueOf(config.getChargeTimeInterval());
					if (joinDate < countDate) {
					//2.1租客入网日期小于房东规定的账单日期时，首次账单生成时间为：租客入网当月的规定账单日00:00:00
						firstOrderTime = DateUtils.buildDate(joinYear, joinMonth, countDate);
					} else {
					//2.2租客入网日大于等于房东规定账单日期时，首次账单生成时间为：租客入网次月的规定账单日00:00:00
						firstOrderTime = DateUtils.buildDate(joinYear, (joinMonth + 1), countDate);
					}
				}
				req.setFirstOrderTime(firstOrderTime);

				long r = jedisUtil.setnx(CacheKey.CACHE_KEY_RENTER_ID + renterId, String.valueOf(renterId), 1000L);
				if (r == 0) {
					result.set(ResultCode.ERROR_201, "操作进行中，请勿重复操作");
					return result;
				}
				renterService.saveConfig(req);
			} else { //编辑配置
				if (!isConfigExist(configId, renterId)) {
					result.set(ResultCode.ERROR_201, "配置ID不合法");
					return result;
				}
				if (endTime != null) {
					Date oneMonthAfterToday = DateUtils.dateAddMonths(new Date(), 1);
					if (endTime.before(oneMonthAfterToday)) {
						result.set(ResultCode.ERROR_201, "结束日期请选择一个月之后的日期");
						return result;
					}
				}
				
				long r = jedisUtil.setnx(CacheKey.CACHE_KEY_SWITCH_RENTER_ID + renterId, String.valueOf(renterId), 1000L);
				if (r == 0) {
					result.set(ResultCode.ERROR_201, "操作进行中，请勿重复操作");
					return result;
				}
				renterService.updateConfig(req);
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
		}
		return result;
	}
	
	@Override
	public Boolean isConfigExist(Long configId, Long renterId){
		return renterService.isConfigExist(configId, renterId);
	}
	
	@Override
	public ConfigDetailResult getRenterConfig(DetailReq req){
		ConfigDetailResult result = new ConfigDetailResult();
		try {
			if (null == req.getUid() || null == req.getRenterId()) {
				result.set(ResultCode.ERROR_201, "客户uid和租客ID均不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(req.getUid());
			if (!userInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			UserInfo userInfo = userInfoResult.getMemberInfo();
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			RenterVO renterVO = renterService.getById(req.getRenterId(), companyId);
			if (null == renterVO) {
				result.set(ResultCode.ERROR_201, "租客ID不合法");
				return result;
			}
			RenterConfigVO renterConfigVO = renterService.getRenterConfigVO(req.getRenterId());
			result.setRenterConfigVO(renterConfigVO);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
		}
		return result;
	}
	
	/**
	 * 租客开合闸操作
	 * @param uid       客户uid
	 * @param token       token
	 * @param renterId  租客ID
	 * @param type      开合闸类型  0:开闸操作       1:合闸操作
	 * @param pwd       操作密码
	 */
	@Override
	public Result renterSwitch(Long uid, String token, Long renterId, Integer type, String pwd, Long meterId){
		Result result = new DefaultResult();
		try {
			if (null == uid || null == renterId || null == meterId) {
				result.set(ResultCode.ERROR_201, "客户uid、租客ID和meterID均不能为空");
				return result;
			}
			GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
			if (!userInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			UserInfo userInfo = userInfoResult.getMemberInfo();
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "客户uid不合法");
				return result;
			}
			if (null == userInfo.getSwitchpwd()) {
				result.set(ResultCode.ERROR_401, "尚未设置操作密码，请先设置操作密码");
				return result; 
			}
			// 判断密码是否合法
			if (StringUtils.isBlank(pwd)) {
				result.set(ResultCode.ERROR_201, "操作密码不能为空");
				return result;
			}
			boolean checkSwitchpwdResult = userInfoService.checkSwitchpwd(uid, pwd);
			if (!checkSwitchpwdResult) {
				result.set(ResultCode.ERROR_201, "操作密码不正确");
				return result;
			}
			RenterVO renterVO = renterService.getById(renterId, companyId);
			if (null == renterVO) {
				result.set(ResultCode.ERROR_201, "租客关系不存在，操作不合法");
				return result;
			}
			if (!RenterStatus.isValueExist(type)) {
				result.set(ResultCode.ERROR_201, "操作指令不合法");
				return result;
			}
			if (type.equals(renterVO.getSwitchStatus())) {
				result.set(ResultCode.ERROR_201, "租客阀门正处于该状态，不可重复操作");
				return result;
			}
			// 合闸操作判断
			if (type.equals(RenterStatus.NORMAL.getValue())) {
				// 判断是否存在租客配置文件，如果没有则不能进行合闸操作
				Boolean exist = renterService.isConfigExist(null, renterId);
				if (!exist) {
					result.set(ResultCode.ERROR_201, "请先进行租客用电配置，然后在进行合闸操作");
					return result;
				}
			}
			// 获取租客所有仪表
			List<RenterMeter> meters = renterMeterService.getMeters(renterId);
			boolean isExistThisMeter = false;
			String meterName = "";
			List<String> meterNos = new ArrayList<>();
			for (RenterMeter renterMeter : meters) {
				if (meterId.equals(renterMeter.getMeterId())) {//获取公司仪表ID
					isExistThisMeter = true;
					meterNos.add(renterMeter.getMeterNo());
					meterName =renterMeter.getName();
					break;
				}
			}
			//判断该租客是否存在该仪表
			if (!isExistThisMeter) {
				result.set(ResultCode.ERROR_201, "该租客不存在这块仪表");
				return result;
			}
			// 判断是否是公摊仪表，如果是公摊仪表则不执行开合闸操作
			boolean isPublic = renterMeterService.isPublicMeter(meterId);
			if (isPublic) {
				result.set(ResultCode.ERROR_201, "该仪表是公摊仪表，不可操作");
				return result;
			}
			
			long r = jedisUtil.setnx(CacheKey.CACHE_KEY_SWITCH_RENTER_ID + renterId, String.valueOf(renterId), 1000L);
			if (r == 0) {
				result.set(ResultCode.ERROR_201, "操作进行中，请勿重复操作");
				return result;
			}
			NetDauControlRequest netDauControlRequest=new NetDauControlRequest();
			netDauControlRequest.setUid(uid);
			netDauControlRequest.setToken(token);
			/*List<String> meterIds=new ArrayList<>();
			meterIds.add("330100A00201003");*/
			netDauControlRequest.setMeterIds(meterNos);
			String cmd = type.equals(RenterStatus.FORBIDEN.getValue()) ? "tripping" : "closing";
			netDauControlRequest.setCmd(cmd);
			/*netDauControlRequest.setCmd("closing");//合闸closing, 跳闸tripping */
			netDauControlRequest.setRenterId(renterId);
			
			NetDauControlResponse response=netDauControlService.control(netDauControlRequest);
			if(response.getResultCode()!=ResultCode.SUCCESS){//只表示调用成功，还不知道命令是否处理成功
				result.setResultCode(response.getResultCode());
	            result.setResultMessage(response.getResultMessage());
	            return result; 
			}
			
			// 消息推送
			pushMessageToCustomer(uid, userInfo.getMobile(), meterName);
			pushMessageToRenter(renterVO.getMemberId(), renterVO.getMobile(), meterName, userInfoResult.getCompanyInfo().getName());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("renter switch:{}", e);
		}
		return result;
	}

	//给房东推送
	private void pushMessageToCustomer(Long uid, String mobile, String meterName){
		try {
			if (null == uid || StringUtils.isBlank(mobile) || StringUtils.isBlank(meterName)) {
				return;
			}
			MessageId messageId = MessageId.SWITCH_OPERATION;
			Map<String, String> params = new HashMap<>();
			params.put("meter_name", meterName);
			EXECUTOR_SERVICE.submit(new Runnable() {
				@Override
				public void run() {
					messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
				}
			});
		} catch (Exception e) {
			 logger.error("给房东推送消息异常", e);
		}
	}
	
	//给租客推送消息
	private void pushMessageToRenter(Long uid, String mobile, String meterName, String companyName){
		try {
			if (null == uid || StringUtils.isBlank(mobile) || StringUtils.isBlank(meterName)) {
				return;
			}
			MessageId messageId = MessageId.RENTER_SWITHED;
			Map<String, String> params = new HashMap<>();
			params.put("meter_name", meterName);
			params.put("company_name", companyName);
			EXECUTOR_SERVICE.submit(new Runnable() {
				
				@Override
				public void run() {
					messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
				}
			});
		} catch (Exception e) {
			logger.error("给租客推送消息异常", e);
		}
	}
	
	
	/**
	 * 创建一个固定线程池
	 */
	private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
			1, Runtime.getRuntime().availableProcessors(), 
			60, TimeUnit.SECONDS, 
			new SynchronousQueue<Runnable>(), // 工作队列
			new ThreadPoolExecutor.CallerRunsPolicy());// 线程池饱和处理策略
	
}
