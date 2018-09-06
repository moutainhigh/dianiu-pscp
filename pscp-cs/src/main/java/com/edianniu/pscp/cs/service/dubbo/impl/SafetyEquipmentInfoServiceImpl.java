package com.edianniu.pscp.cs.service.dubbo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cs.bean.DefaultResult;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.TimeUnit;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.bean.safetyequipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.DeleteResult;
import com.edianniu.pscp.cs.bean.safetyequipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.DetailsResult;
import com.edianniu.pscp.cs.bean.safetyequipment.ListReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.ListResult;
import com.edianniu.pscp.cs.bean.safetyequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.SaveResult;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.RoomService;
import com.edianniu.pscp.cs.service.SafetyEquipmentService;
import com.edianniu.pscp.cs.service.dubbo.SafetyEquipmentInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("safetyEquipmentInfoService")
public class SafetyEquipmentInfoServiceImpl implements SafetyEquipmentInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(SafetyEquipmentInfoServiceImpl.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("roomService")
	private RoomService roomService;
	
	@Autowired
	@Qualifier("safetyEquipmentService")
	private SafetyEquipmentService safetyEquipmentService;
	
	/**
	 * 配电房安全用具保存
	 * @param saveReqData
	 * @return
	 */
	@Override
	public SaveResult saveSafetyEquipment(SaveReqData saveReqData){
		SaveResult result = new SaveResult();
		try {
			Long uid = saveReqData.getUid();
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
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
			if (null == companyInfo) {
				result.set(ResultCode.ERROR_201, "用户公司信息不存在");
				return result;
			}
            if (null == userInfo.getLoginName() || 0L == userInfo.getCompanyId()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "请进行实名认证");
                return result;
            }
            if (!userInfo.isCustomer()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能为配电房添加安全用具");
                return result;
            }
			if (null == saveReqData.getRoomId()) {
				result.set(ResultCode.ERROR_401, "需要关联配电房");
				return result;
			}
			RoomVO roomVO = roomService.queryRoomVO(saveReqData.getRoomId());
			if (null == roomVO ) {
				result.set(ResultCode.ERROR_401, "关联的配电房不存在");
				return result;
			}
			String name = saveReqData.getName();
			if (! BizUtils.checkLength(name, 50)) {
				result.set(ResultCode.ERROR_402, "名称不能为空或超过50个字");
				return result;
			}
			String serialNumber = saveReqData.getSerialNumber();
			if (! BizUtils.checkLength(serialNumber, 30)) {
				result.set(ResultCode.ERROR_403, "序列号不能为空或超过30个字");
				return result;
			}
			String modelNumber = saveReqData.getModelNumber();
			if (! BizUtils.checkLength(modelNumber, 30)) {
				result.set(ResultCode.ERROR_404, "型号不能为空或超过30个字");
				return result;
			}
			String voltageLevel = saveReqData.getVoltageLevel();
			if (! BizUtils.checkLength(voltageLevel, 20)) {
				result.set(ResultCode.ERROR_404, "电压等级不能为空或超过20个字");
				return result;
			}
			String manufacturer = saveReqData.getManufacturer();
			if (! BizUtils.checkLength(manufacturer, 50)) {
				result.set(ResultCode.ERROR_404, "生产厂家不能为空或超过50个字");
				return result;
			}
			Integer testCycle = saveReqData.getTestCycle();
			Integer testTimeUnit = saveReqData.getTestTimeUnit();
			if (null == testCycle || null == testTimeUnit) {
				result.set(ResultCode.ERROR_405, "试验周期不能为空");
				return result;
			}
			if (testCycle < 1 || testCycle > 31) {
				result.set(ResultCode.ERROR_405, "周期请输入1-31之间的整数");
				return result;
			}
			if (! TimeUnit.isExist(testTimeUnit)) {
				result.set(ResultCode.ERROR_405, "时间单位不合法");
				return result;
			}
			if (StringUtils.isBlank(saveReqData.getInitialTestDate())) {
				result.set(ResultCode.ERROR_406, "初始检查时间不能为空");
				return result;
			}
			Date initialTestDate = DateUtils.parse(saveReqData.getInitialTestDate(), DateUtils.DATE_PATTERN);
			if (null == initialTestDate) {
				result.set(ResultCode.ERROR_406, "初始检查时间不合法");
				return result;
			}
			if (initialTestDate.before(DateUtils.getStartDate(new Date()))) {
				result.set(ResultCode.ERROR_406, "初始检查日期不能早于添加日期");
				return result;
			}
			String remark = saveReqData.getRemark();
			if (StringUtils.isNotEmpty(remark)) {
				if (!BizUtils.checkLength(remark, 1000)) {
					result.set(ResultCode.ERROR_406, "备注不能超过1000个字");
					return result;
				}
			}
			// 保存安全用具
			safetyEquipmentService.saveSafetyEquipment(saveReqData, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("save:{}", e);
		}
		return result;
	}
	
	/**
	 * 配电房安全用具列表
	 */
	@Override
	public ListResult safetyEquipmentList(ListReqData listReqData){
		ListResult result = new ListResult();
		try {
			if (null == listReqData.getUid()) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(listReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long roomId = listReqData.getRoomId();
			if (roomId != null && roomId != -1) {  //如果等于-1或null，则查询所有配电房的安全用具
				RoomVO roomVO = roomService.queryRoomVO(roomId);
				if (null == roomVO) {
					result.set(ResultCode.ERROR_402, "关联的配电房不存在");
					return result;
				}
			}
			PageResult<SafetyEquipmentVO> pageResult = safetyEquipmentService.getSafetyEquipmentVOList(listReqData, userInfo);
			
			result.setSafetyEquipments(pageResult.getData());
			result.setTotalCount(pageResult.getTotal());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("list:{}", e);
		}
		return result;
	}
	
	/**
	 * 配电房安全用具详情
	 * @param detailsReqData
	 * @return
	 */
	@Override
	public DetailsResult getSafetyEquipmentDetails(DetailsReqData detailsReqData){
		DetailsResult result = new DetailsResult();
		try {
			if (null == detailsReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(detailsReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = detailsReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "安全用具ID不能为空");
				return result;
			}
			SafetyEquipmentVO safetyEquipmentVO = safetyEquipmentService.getSafetyEquipmentDetails(id);
			result.setSafetyEquipment(safetyEquipmentVO);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("details:{}", e);
		}
		return result;
	}
	
	/**
	 * 配电房安全用具删除
	 * @param deleteReqData
	 * @return
	 */
	@Override
	public DeleteResult deleteSafetyEquipment(DeleteReqData deleteReqData){
		DeleteResult result = new DeleteResult();
		try {
			if (null == deleteReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(deleteReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = deleteReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "安全用具ID不能为空");
				return result;
			}
			SafetyEquipmentVO safetyEquipmentVO = safetyEquipmentService.getSafetyEquipmentDetails(id);
			if (null == safetyEquipmentVO) {
				result.set(ResultCode.ERROR_402, "要删除的安全用具不存在");
				return result;
			}
			safetyEquipmentService.deleteSafetyEquipment(id, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("delete:{}", e);
		}
		return result;
	}
	
	/**
	 * 扫描进入检查周期范围内的安全用具
	 */
	@Override
	public List<Long> scanCanCheckSafetyEquipments(){
		return safetyEquipmentService.scanCanCheckSafetyEquipments();
	}
	
	/**
	 * 处理进入检查周期范围内的安全用具
	 * @param id
	 * @return
	 */
	@Override
	public Result handleCanCheckSafetyEquipment(Long id){
		Result result = new DefaultResult();
		try {
			if (null == id) {
				result.set(ResultCode.ERROR_401, "ID不能为空");
				return result;
			}
			SafetyEquipmentVO equipmentVO = safetyEquipmentService.getSafetyEquipmentDetails(id);
			if (null == equipmentVO) {
				result.set(ResultCode.ERROR_402, "设备不存在");
				return result;
			}
			int t = safetyEquipmentService.handleCanCheckSafetyEquipment(id);
			if (t < 1) {
				result.set(ResultCode.ERROR_403, "处理进入检查周期范围内的安全用具失败");
				return result;
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleCanCheckSafetyEquipment:{}", e);
		}
		return result;
	}

	/**
	 * 扫描所有到达检查截止日期还未检查的安全用具
	 */
	@Override
	public List<Long> scanNoCheckedSafetyEquipments(){
		return safetyEquipmentService.scanNoCheckedSafetyEquipments();
	}
	
	/**
	 * 处理所有到达检查截止日期还未检查的安全用具
	 */
	@Override
	public Result handleNoCheckSafetyEquipment(Long id){
		Result result = new DefaultResult();
		try {
			if (null == id) {
				result.set(ResultCode.ERROR_401, "ID不能为空");
				return result;
			}
			SafetyEquipmentVO equipmentVO = safetyEquipmentService.getSafetyEquipmentDetails(id);
			if (null == equipmentVO) {
				result.set(ResultCode.ERROR_402, "设备不存在");
				return result;
			}
			int t = safetyEquipmentService.handleNoCheckSafetyEquipment(id);
			if (t < 1) {
				result.set(ResultCode.ERROR_403, "处理到达检查截止日期还未检查的安全用具失败");
				return result;
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleCanCheckSafetyEquipment:{}", e);
		}
		return result;
	}
	
	
}
