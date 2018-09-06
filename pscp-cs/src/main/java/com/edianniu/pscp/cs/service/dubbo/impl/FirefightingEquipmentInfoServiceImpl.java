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
import com.edianniu.pscp.cs.bean.firefightingequipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.DeleteResult;
import com.edianniu.pscp.cs.bean.firefightingequipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.DetailsResult;
import com.edianniu.pscp.cs.bean.firefightingequipment.ListReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.ListResult;
import com.edianniu.pscp.cs.bean.firefightingequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.SaveResult;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.FirefightingEquipmentService;
import com.edianniu.pscp.cs.service.RoomService;
import com.edianniu.pscp.cs.service.dubbo.FirefightingEquipmentInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("firefightingEquipmentInfoService")
public class FirefightingEquipmentInfoServiceImpl implements FirefightingEquipmentInfoService {
	private static final Logger logger = LoggerFactory.getLogger(FirefightingEquipmentInfoServiceImpl.class);
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("roomService")
	private RoomService roomService;
	
	@Autowired
	@Qualifier("firefightingEquipmentService")
	private FirefightingEquipmentService firefightingEquipmentService;
	
	/**
	 * 消防栓设施保存
	 */
	@Override
	public SaveResult saveFirefightingEquipment(SaveReqData saveReqData){
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
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能为配电房添加消防设施");
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
			if (! BizUtils.checkLength(name, 200)) {
				result.set(ResultCode.ERROR_402, "名称不能为空或超过200个字");
				return result;
			}
			String boxNumber = saveReqData.getBoxNumber();
			if (! BizUtils.checkLength(boxNumber, 30)) {
				result.set(ResultCode.ERROR_403, "箱号不能为空或超过30个字");
				return result;
			}
			String serialNumber = saveReqData.getSerialNumber();
			if (! BizUtils.checkLength(serialNumber, 30)) {
				result.set(ResultCode.ERROR_404, "编号不能为空或超过30个字");
				return result;
			}
			String placementPosition = saveReqData.getPlacementPosition();
			if (! BizUtils.checkLength(placementPosition, 512)) {
				result.set(ResultCode.ERROR_405, "存放位置不能为空或超过512个字");
				return result;
			}
			String specifications = saveReqData.getSpecifications();
			if (! BizUtils.checkLength(specifications, 50)) {
				result.set(ResultCode.ERROR_406, "规格不能为空或超过50个字");
				return result;
			}
			Integer indoorOrOutdoor = saveReqData.getIndoorOrOutdoor();
			if (null == indoorOrOutdoor) {
				result.set(ResultCode.ERROR_407, "户内、户外选择不能为空");
				return result;
			}
			if ((0 != indoorOrOutdoor) && (1 != indoorOrOutdoor)) {
				result.set(ResultCode.ERROR_407, "户内、户外选择不合法");
				return result;
			}
			if (StringUtils.isBlank(saveReqData.getProductionDate())) {
				result.set(ResultCode.ERROR_408, "生产日期不能为空");
				return result;
			}
			Date productionDate = DateUtils.parse(saveReqData.getProductionDate(), DateUtils.DATE_PATTERN);
			if (null == productionDate) {
				result.set(ResultCode.ERROR_408, "生产日期不合法");
				return result;
			}
			if (StringUtils.isBlank(saveReqData.getExpiryDate())) {
				result.set(ResultCode.ERROR_408, "有效期限不能为空");
				return result;
			}
			Date expiryDate = DateUtils.parse(saveReqData.getExpiryDate(), DateUtils.DATE_PATTERN);
			if (null == expiryDate) {
				result.set(ResultCode.ERROR_408, "有效期限不合法");
				return result;
			}
			if (StringUtils.isBlank(saveReqData.getInitialTestDate())) {
				result.set(ResultCode.ERROR_408, "初始检查时间不能为空");
				return result;
			}
			Date initialTestDate = DateUtils.parse(saveReqData.getInitialTestDate(), DateUtils.DATE_PATTERN);
			if (null == initialTestDate) {
				result.set(ResultCode.ERROR_408, "初始检查时间不合法");
				return result;
			}
			if (initialTestDate.before(DateUtils.getStartDate(new Date()))) {
				result.set(ResultCode.ERROR_408, "初始检查日期不能早于添加日期");
				return result;
			}
			Integer viewCycle = saveReqData.getViewCycle();
			Integer viewTimeUnit = saveReqData.getViewTimeUnit();
			if (null == viewCycle || null == viewTimeUnit) {
				result.set(ResultCode.ERROR_409, "试验周期不能为空");
				return result;
			}
			if (viewCycle < 1 || viewCycle > 31) {
				result.set(ResultCode.ERROR_409, "周期请输入1-31之间的整数");
				return result;
			}
			if (! TimeUnit.isExist(viewTimeUnit)) {
				result.set(ResultCode.ERROR_409, "时间单位不合法");
				return result;
			}
			// 保存消防设施
			firefightingEquipmentService.saveFirefightingEquipment(saveReqData, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("save:{}", e);
		}
		return result;
	}
	
	/**
	 * 消防设施列表
	 * @param listReqData
	 * @return
	 */
	@Override
	public ListResult firefightingEquipmentList(ListReqData listReqData){
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
			PageResult<FirefightingEquipmentVO> pageResult = firefightingEquipmentService.getFirefightingEquipmentVOList(listReqData, userInfo);
			
			result.setFirefightingEquipments(pageResult.getData());
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
	 * 消防设施详情
	 */
	@Override
	public DetailsResult getFriefightingEquipmentDetails(DetailsReqData detailsReqData){
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
			FirefightingEquipmentVO fightingEquipmentVO = firefightingEquipmentService.getFirefightingEquipmentDetails(id);
			result.setFirefightingEquipment(fightingEquipmentVO);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("details:{}", e);
		}
		return result;
	}
	
	/**
	 * 消防设施删除
	 * @param deleteReqData
	 * @return
	 */
	@Override
	public DeleteResult deleteFirefightingEquipment(DeleteReqData deleteReqData){
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
			FirefightingEquipmentVO firefightingEquipmentDetails = firefightingEquipmentService.getFirefightingEquipmentDetails(id);
			if (null == firefightingEquipmentDetails) {
				result.set(ResultCode.ERROR_402, "要删除的安全用具不存在");
				return result;
			}
			firefightingEquipmentService.deleteFirefightingEquipment(id, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("delete:{}", e);
		}
		return result;
	}
	
	/**
	 * 扫描进入检查周期范围内的消防设施
	 */
	@Override
	public List<Long> scanCanCheckFirefightingEquipments(){
		return firefightingEquipmentService.scanCanCheckFirefightingEquipments();
	}
	
	/**
	 * 处理进入检查周期范围内的消防设施
	 * @param id
	 * @return
	 */
	@Override
	public Result handleCanCheckFirefightingEquipment(Long id){
		Result result = new DefaultResult();
		try {
			if (null == id) {
				result.set(ResultCode.ERROR_401, "ID不能为空");
				return result;
			}
			FirefightingEquipmentVO equipmentVO = firefightingEquipmentService.getFirefightingEquipmentDetails(id);
			if (null == equipmentVO) {
				result.set(ResultCode.ERROR_402, "设备不存在");
				return result;
			}
			int t = firefightingEquipmentService.handleCanCheckFirefightingEquipment(id);
			if (t < 1) {
				result.set(ResultCode.ERROR_403, "处理进入检查周期范围内的消防设施失败");
				return result;
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleCanCheckFirefightingEquipment:{}", e);
		}
		return result;
	}
	
	
	
}
